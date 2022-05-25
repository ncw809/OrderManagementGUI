package com.ncw809.ordermanagementgui.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.ncw809.ordermanagementgui.commons.APIClient;
import com.ncw809.ordermanagementgui.commons.APIInterface;
import com.ncw809.ordermanagementgui.commons.MenuListResponseFormat;
import com.ncw809.ordermanagementgui.commons.ResponseFormat;
import com.ncw809.ordermanagementgui.commons.SaveVisitResponseFormat;
import com.ncw809.ordermanagementgui.vo.Menu;
import com.ncw809.ordermanagementgui.vo.MenuPair;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Kiosk extends JFrame {

	String method[] = {"카드", "현금", "쿠폰"};
	String platform[] = {"키오스크", "배달의민족", "쿠팡이츠", "요기요"};

	APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
	List<Menu> menus = new ArrayList<Menu>();
	List<JButton> buttons = new ArrayList<JButton>();
	List<MenuPair> menuPairList = new ArrayList<MenuPair>();
	JPanel menuPanel = new JPanel();
	JTextArea order = new JTextArea();

	public Kiosk(String token, Long storeSeq){
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		Call<MenuListResponseFormat> call = apiInterface.getMenuListByStoreSeq(storeSeq, token);
		call.enqueue(new Callback<MenuListResponseFormat>() {
			@Override
			public void onResponse(Call<MenuListResponseFormat> call, Response<MenuListResponseFormat> response) {
				MenuListResponseFormat result = response.body();
				if (response.code() == 200){
					menus = result.data.menus;
					int rows;
					if (menus.size() % 3 == 0){
						rows = menus.size() / 3;
					} else {
						rows = menus.size() / 3 + 1;
					}
					menuPanel.setLayout(new GridLayout(rows, 3));
					for (int i=0; i < menus.size(); i++){
						buttons.add(new JButton(menus.get(i).getName()));
						buttons.get(i).addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								for (Menu m : menus){
									if (m.getName().equals(((JButton) e.getSource()).getText())){
										Quantity q = new Quantity(order, m, menuPairList);
										break;
									}
								}
							}
						});
						menuPanel.add(buttons.get(i));
					}
					menuPanel.revalidate();
					menuPanel.repaint();
				} else {
					JOptionPane.showMessageDialog(null, "다시 선택해주세요.");
				}
			}

			@Override
			public void onFailure(Call<MenuListResponseFormat> call, Throwable t) {
				call.cancel();
			}
		});

		JComboBox<String> combo1 = new JComboBox<String>(method);
		JComboBox<String> combo2 = new JComboBox<String>(platform);
		JButton orderBtn = new JButton("주문");
		JPanel panel = new JPanel();

		orderBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (order.getText().equals("")){
					return;
				}

				String method;
				switch (combo1.getSelectedIndex()){
					case 0:
						method = "METHOD_CARD";
						break;
					case 1:
						method = "METHOD_CASH";
						break;
					case 2:
						method = "METHOD_COUPON";
						break;
					default:
						return;
				}

				String platform;
				switch (combo2.getSelectedIndex()){
					case 0:
						platform = "PLATFORM_KIOSK";
						break;
					case 1:
						platform = "PLATFORM_BAEMIN";
						break;
					case 2:
						platform = "PLATFORM_EATS";
						break;
					case 3:
						platform = "PLATFORM_YOGIYO";
						break;
					default:
						return;
				}

				String orders;
				orders = MenuPair.toString(menuPairList);

				Long totalPrice = Long.valueOf(0);
				for (MenuPair mp : menuPairList){
					totalPrice = totalPrice + mp.getPrice();
				}

				Call<SaveVisitResponseFormat> call1 = apiInterface.saveVisit(token, storeSeq);
				Long finalTotalPrice = totalPrice;
				call1.enqueue(new Callback<SaveVisitResponseFormat>() {
					@Override
					public void onResponse(Call<SaveVisitResponseFormat> call,
						Response<SaveVisitResponseFormat> response) {
						SaveVisitResponseFormat result = response.body();
						if (response.code() == 200){
							Long visitSeq = result.data.seq;
							Call<ResponseFormat> call2 = apiInterface.saveOrder(token, orders, method, platform,
								finalTotalPrice, visitSeq, storeSeq);
							call2.enqueue(new Callback<ResponseFormat>() {
								@Override
								public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
									ResponseFormat result = response.body();
									if (response.code() == 200){
										JOptionPane.showMessageDialog(null, "주문 성공");
										new SelectStore(token, "KIOSK");
										dispose();
									} else {
										JOptionPane.showMessageDialog(null, "주문 실패");
									}
								}

								@Override
								public void onFailure(Call<ResponseFormat> call, Throwable t) {
									call2.cancel();
								}
							});
						} else {
							JOptionPane.showMessageDialog(null, "주문 실패");
						}
					}

					@Override
					public void onFailure(Call<SaveVisitResponseFormat> call, Throwable t) {
						call1.cancel();
					}
				});
				dispose();
			}
		});

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(combo1);
		panel.add(combo2);
		panel.add(orderBtn);

		add(menuPanel);
		add(order);
		add(panel);

		setTitle("메뉴 선택");
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
