package com.ncw809.ordermanagementgui.gui;

import javax.swing.*;

public class Mypage extends JFrame {

	String token;

	public Mypage(String token) {
		this.token = token;
		getMyInfo();
	}

	public void getMyInfo(){
		System.out.println("test");

	}
}
