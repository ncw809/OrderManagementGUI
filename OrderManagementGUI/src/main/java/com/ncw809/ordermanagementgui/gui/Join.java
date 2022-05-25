package com.ncw809.ordermanagementgui.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.ncw809.ordermanagementgui.commons.APIClient;
import com.ncw809.ordermanagementgui.commons.APIInterface;
import com.ncw809.ordermanagementgui.commons.ResponseFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Join extends JFrame {

	public Join(){
		setTitle("회원가입");
		setSize(280,250);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel idLabel = new JLabel("ID");
		idLabel.setBounds(10, 10, 80, 25);

		JLabel password1Label = new JLabel("Password1");
		password1Label.setBounds(10, 40, 80, 25);

		JLabel password2Label = new JLabel("Password2");
		password2Label.setBounds(10, 70, 80, 25);

		JLabel phoneNumberLabel = new JLabel("Phone Number");
		phoneNumberLabel.setBounds(10, 100, 80, 25);

		JLabel nameLabel = new JLabel("name");
		nameLabel.setBounds(10, 130, 80, 25);

		JTextField idField = new JTextField(20);
		idField.setBounds(100, 10, 160, 25);

		JPasswordField password1Field = new JPasswordField(20);
		password1Field.setBounds(100, 40, 160, 25);

		JPasswordField password2Field = new JPasswordField(20);
		password2Field.setBounds(100, 70, 160, 25);

		JTextField phoneNumberField = new JTextField(20);
		phoneNumberField.setBounds(100, 100, 160, 25);

		JTextField nameField = new JTextField(20);
		nameField.setBounds(100, 130, 160, 25);

		JButton joinBtn = new JButton("회원가입");
		joinBtn.setBounds(10, 170, 250, 25);
		joinBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = idField.getText();
				String password1 = String.valueOf(password1Field.getPassword());
				String password2 = String.valueOf(password2Field.getPassword());
				String phoneNumber = phoneNumberField.getText();
				String name = nameField.getText();

				if (id.equals("")){
					JOptionPane.showMessageDialog(null, "ID를 입력해주세요.");
				} else if (password1.equals("")) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.");
				} else if (!password1.equals(password2)){
					JOptionPane.showMessageDialog(null, "비밀번호가 틀립니다.");
				} else if (phoneNumber.equals("")){
					JOptionPane.showMessageDialog(null, "전화번호를 입력해주세요.");
				} else if (name.equals("")){
					JOptionPane.showMessageDialog(null, "이름을 입력해주세요.");
				} else {
					APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
					Call<ResponseFormat> call = apiInterface.signup(id, password1, password2, phoneNumber, name, "ROLE_USER");
					call.enqueue(new Callback<ResponseFormat>() {
						@Override
						public void onResponse(Call<ResponseFormat> call,
							Response<ResponseFormat> response) {
							ResponseFormat result = response.body();
							if (response.code() == 200){
								JOptionPane.showMessageDialog(null, "회원가입 성공");
								new Login();
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, "회원가입에 실패했습니다.");
							}
						}

						@Override
						public void onFailure(Call<ResponseFormat> call, Throwable t) {
							call.cancel();
						}
					});
				}
			}
		});

		panel.add(idLabel);
		panel.add(password1Label);
		panel.add(password2Label);
		panel.add(phoneNumberLabel);
		panel.add(nameLabel);
		panel.add(idField);
		panel.add(password1Field);
		panel.add(password2Field);
		panel.add(nameField);
		panel.add(phoneNumberField);
		panel.add(joinBtn);

		add(panel);
	}
}
