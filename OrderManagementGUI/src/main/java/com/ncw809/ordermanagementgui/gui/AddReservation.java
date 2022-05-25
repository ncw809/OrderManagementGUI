package com.ncw809.ordermanagementgui.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.*;

import net.sourceforge.jdatepicker.impl.UtilDateModel;

import com.ncw809.ordermanagementgui.commons.APIClient;
import com.ncw809.ordermanagementgui.commons.APIInterface;
import com.ncw809.ordermanagementgui.commons.ResponseFormat;
import com.ncw809.ordermanagementgui.commons.TableListResponseFormat;
import com.ncw809.ordermanagementgui.vo.Table;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReservation extends JFrame {

	APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
	String token;
	Long storeSeq;
	int tableCnt;
	Long max = 0L;

	DatePicker datePicker = new DatePicker();
	JButton searchBtn = new JButton("조회");
	JComboBox<String> combo;
	JPanel datePanel = new JPanel();
	JPanel timePanel = new JPanel();
	JPanel reservationPanel = new JPanel();

	public AddReservation(String token, Long storeSeq)  {
		this.token = token;
		this.storeSeq = storeSeq;
		getTableCnt();
		while(max == 0){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
		timePanel.setLayout(new GridLayout(4, 4));
		reservationPanel.setLayout(new BoxLayout(reservationPanel, BoxLayout.X_AXIS));

		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (datePicker.getModel().getValue() == null) {
					JOptionPane.showMessageDialog(null, "날짜 미선택");
					return;
				}

				setTimeBtn();
			}
		});

		combo = new JComboBox<String>();
		for (int i = 0; i < max; i++){
			combo.addItem((i + 1) + "명");
		}
		combo.setMaximumSize(new Dimension(1000, 300));

		datePanel.add(datePicker.getDatePicker());
		datePanel.add(searchBtn);

		add(datePanel);
		add(combo);
		add(timePanel);

		setTitle("시간 선택");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(400, 300);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void getTableCnt(){
		Call<TableListResponseFormat> call = apiInterface.getTableListByStoreSeq(storeSeq, token);
		call.enqueue(new Callback<TableListResponseFormat>() {
			@Override
			public void onResponse(Call<TableListResponseFormat> call, Response<TableListResponseFormat> response) {
				TableListResponseFormat result = response.body();
				if (response.code() == 200){
					tableCnt = result.data.tables.size();
					for (Table t : result.data.tables){
						if (t.getPlaces() > max){
							max = t.getPlaces();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "연결 실패");
				}
			}

			@Override
			public void onFailure(Call<TableListResponseFormat> call, Throwable t) {
				call.cancel();
			}
		});
	}

	public void setTimeBtn(){
		timePanel.removeAll();
		UtilDateModel model = datePicker.getModel();
		Date date = model.getValue();
		LocalDate selectDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalTime basetime = LocalTime.of(11, 00);
		for (int i = 0; i < 16; i++){
			basetime = basetime.plusMinutes(30);
			JButton btn = new JButton(basetime.toString());
			if (selectDate.equals(LocalDate.now())
				&& basetime.isAfter(LocalTime.now())){
				btn.setEnabled(true);
			} else if (date.before(new Date())){
				btn.setEnabled(false);
			}

			LocalTime finalBasetime = basetime;
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int answer = JOptionPane.showConfirmDialog(
						null,
						selectDate +" "+ finalBasetime +"에 "+combo.getSelectedItem()+" 예약하시겠습니까?",
						"예약 확인",
						JOptionPane.YES_NO_OPTION);

					if (answer == JOptionPane.YES_OPTION){
						addReservation(selectDate, finalBasetime);
					}
				}
			});
			timePanel.add(btn);
		}
		revalidate();
		repaint();
	}

	public void addReservation(LocalDate date, LocalTime time){
		LocalDateTime reservationTime = LocalDateTime.of(date, time);
		Call<ResponseFormat> call = apiInterface.addReservation(
			token,
			(long)(combo.getSelectedIndex() + 1),
			reservationTime,
			storeSeq
		);
		call.enqueue(new Callback<ResponseFormat>() {
			@Override
			public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
				ResponseFormat result = response.body();
				if (response.code() == 200){
					JOptionPane.showMessageDialog(null, "예약 성공");
					new SelectStore(token, "USER");
					dispose();
				} else{
					JOptionPane.showMessageDialog(null, "예약 실패");
				}
			}

			@Override
			public void onFailure(Call<ResponseFormat> call, Throwable t) {

			}
		});
	}
}
