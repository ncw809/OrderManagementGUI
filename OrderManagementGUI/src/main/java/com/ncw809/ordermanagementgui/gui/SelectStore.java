package com.ncw809.ordermanagementgui.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.ncw809.ordermanagementgui.commons.APIClient;
import com.ncw809.ordermanagementgui.commons.APIInterface;
import com.ncw809.ordermanagementgui.commons.StoreListResponseFormat;
import com.ncw809.ordermanagementgui.vo.Store;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectStore extends JFrame {

	APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
	List<Store> stores = new ArrayList<Store>();
	String token;
	String authority;
	JPanel mypagePanel = new JPanel();
	JPanel storePanel = new JPanel();
	JButton mypageBtn = new JButton("마이페이지");

	public SelectStore(String token, String authority){
		this.token = token;
		this.authority = authority;

		getStoreList();
		while(stores.size() == 0){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		storePanel.setLayout(new BoxLayout(storePanel, BoxLayout.X_AXIS));
		storePanel.setPreferredSize(new Dimension(1000, 30));
		mypageBtn.setPreferredSize(new Dimension(1000, 30));
		mypageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Mypage(token);
				dispose();
			}
		});
		mypagePanel.add(mypageBtn);

		int rows;
		if (stores.size() % 3 == 0){
			rows = stores.size() / 3;
		} else {
			rows = stores.size() / 3 + 1;
		}
		storePanel.setLayout(new GridLayout(rows, 3));
		for(int i = 0; i < stores.size(); i++){
			JButton btn = new JButton(stores.get(i).getName());
			int finalI = i;
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					switch (authority){
						case "KIOSK":
							new Kiosk(token, stores.get(finalI).getSeq());
							break;
						case "USER":
							new AddReservation(token, stores.get(finalI).getSeq());
							break;
						default:
					}
					dispose();
				}
			});
			storePanel.add(btn);
		}

		add(mypagePanel);
		add(storePanel);
		storePanel.revalidate();
		storePanel.repaint();

		setTitle("가게 선택");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(400, 300);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void getStoreList(){
		Call<StoreListResponseFormat> call = apiInterface.getStoreList(token);
		call.enqueue(new Callback<StoreListResponseFormat>() {
			@Override
			public void onResponse(Call<StoreListResponseFormat> call, Response<StoreListResponseFormat> response) {
				StoreListResponseFormat result = response.body();
				if (response.code() == 200){
					stores = result.data.stores;
				} else {
					JOptionPane.showMessageDialog(null, "다시 선택해주세요.");
				}
			}

			@Override
			public void onFailure(Call<StoreListResponseFormat> call, Throwable t) {
				call.cancel();
			}
		});
	}
}
