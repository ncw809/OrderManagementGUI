package com.ncw809.ordermanagementgui.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.ncw809.ordermanagementgui.vo.Menu;
import com.ncw809.ordermanagementgui.vo.MenuPair;

public class Quantity extends JFrame {

	public Quantity(JTextArea order, Menu m, List<MenuPair> menuPairList) {
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JTextField quantity = new JTextField();
		JButton submitBtn = new JButton("확인");
		List<JButton> buttonList = new ArrayList<JButton>();

		panel1.setLayout(new GridLayout(3, 3));
		for (int i = 0; i < 9; i++){
			buttonList.add(new JButton((i + 1) + ""));
			buttonList.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					quantity.setText(quantity.getText() + ((JButton) e.getSource()).getText());
				}
			});
			panel1.add(buttonList.get(i));
		}

		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MenuPair mp = new MenuPair(m, Long.parseLong(quantity.getText()));
				menuPairList.add(mp);
				order.setText(order.getText() + m.getName() + "\t" + quantity.getText()
					+ "\t" + mp.getPrice() + "\r\n");
				dispose();
			}
		});

		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.setMaximumSize(new Dimension(1000, 300));
		panel2.add(quantity);
		panel2.add(submitBtn);

		add(panel1);
		add(panel2);

		setTitle("메뉴 선택");
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
