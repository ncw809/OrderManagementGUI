package com.ncw809.ordermanagementgui.gui;

public class GUIMain {

	public static Login login;

	public GUIMain(){
		init();
	}

	public static void init(){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI(){
		login = new Login();

	}

}
