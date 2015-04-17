package fr.dilink.notificationAPI.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.dilink.notificationAPI.Notification;

public class MainPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	JButton button;
	JTextField notificationTitle;
	JTextArea notificationDescription;

	Notification notif;

	public MainPanel() {
		this.setBackground(Color.ORANGE);

		button = new JButton("Afficher");
		button.addActionListener(this);
		notificationTitle = new JTextField("Title");
		notificationTitle.setPreferredSize(new Dimension(150, 25));
		notificationDescription = new JTextArea("Description");
		notificationDescription.setPreferredSize(new Dimension(150, 100));

		this.add(button);
		this.add(notificationTitle);
		this.add(notificationDescription);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.button) {
			if(!notificationTitle.getText().equals("") && !notificationDescription.getText().equals("")) {
				notif = new Notification(notificationTitle.getText(), notificationDescription.getText());
				notif.setIcon(MainFrame.class, "icon.png");
				notif.setSoundAlert(MainFrame.class, "bell.wav");
				notif.setBackgroundColor(0xff00ff);
				notif.draw();
			}
		}
	}
}