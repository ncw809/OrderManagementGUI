package com.ncw809.ordermanagementgui.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.ncw809.ordermanagementgui.commons.APIClient;
import com.ncw809.ordermanagementgui.commons.APIInterface;
import com.ncw809.ordermanagementgui.commons.AuthorityCheckResponseFormat;
import com.ncw809.ordermanagementgui.commons.LoginResponseFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends JFrame {

	APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

	public Login(){
		setTitle("로그인");
		setSize(280,150);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel idLabel = new JLabel("ID");
		idLabel.setBounds(10, 10, 80, 25);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);

		JTextField idField = new JTextField(20);
		idField.setBounds(100, 10, 160, 25);

		JPasswordField passwordField = new JPasswordField(20);
		passwordField.setBounds(100, 40, 160, 25);

		JButton joinBtn = new JButton("회원가입");
		joinBtn.setBounds(10, 80, 100, 25);
		joinBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Join();
				dispose();
			}
		});

		JButton loginBtn = new JButton("로그인");
		loginBtn.setBounds(160, 80, 100, 25);
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Call<LoginResponseFormat> call = apiInterface.login(idField.getText(),
					String.valueOf(passwordField.getPassword()));
				call.enqueue(new Callback<LoginResponseFormat>() {
					@Override
					public void onResponse(Call<LoginResponseFormat> call, Response<LoginResponseFormat> response) {
						LoginResponseFormat result = response.body();
						if (response.code() == 200 && result.data.token != null){
							JOptionPane.showMessageDialog(null, "로그인 성공");
							redirectByAuthority(result.data.token);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "로그인에 실패했습니다.");
						}
					}

					@Override
					public void onFailure(Call<LoginResponseFormat> call, Throwable t) {
						call.cancel();
					}
				});
			}
		});

		panel.add(idLabel);
		panel.add(passwordLabel);
		panel.add(idField);
		panel.add(passwordField);
		panel.add(joinBtn);
		panel.add(loginBtn);

		add(panel);
	}

	public void redirectByAuthority(String token){
		Call<AuthorityCheckResponseFormat> call = apiInterface.authorityCheck(token);
		call.enqueue(new Callback<AuthorityCheckResponseFormat>() {
			@Override
			public void onResponse(Call<AuthorityCheckResponseFormat> call,
				Response<AuthorityCheckResponseFormat> response) {
				AuthorityCheckResponseFormat result = response.body();
				if (response.code() == 200) {
					switch (result.data.authority) {
						case "USER":
						case "KIOSK":
							new SelectStore(token, result.data.authority);
							break;
						case "ADMIN":
							break;
					}
				}
			}

			@Override
			public void onFailure(Call<AuthorityCheckResponseFormat> call, Throwable t) {
				call.cancel();
			}
		});
	}
}
