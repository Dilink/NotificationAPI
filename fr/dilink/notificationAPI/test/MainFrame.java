package fr.dilink.notificationAPI.test;

import javax.swing.JFrame;

public class MainFrame extends JFrame
{
	public MainFrame() {
		this.setTitle("JFrame");
		this.setSize(200, 200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(false);
		
		this.add(new MainPanel());
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}