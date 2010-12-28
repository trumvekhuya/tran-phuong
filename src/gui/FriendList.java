package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.ChatClient;

public class FriendList extends JFrame{
	static final long serialVersionUID = 3;
	private JTextField hostTF;
	private JTextField serverPortTF;
	public FriendList(){
		super();
		setTitle("BP&PP");
		Random r = new Random();
		int m = r.nextInt(500);
		System.out.println(m);
		setBounds(m, m/2, 400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		hostTF = new JTextField();
		hostTF.setText("localhost");
		hostTF.setColumns(20);
		panel.add(hostTF);
		
		serverPortTF = new JTextField();
		serverPortTF.setText("2222");
		serverPortTF.setColumns(5);
		panel.add(serverPortTF);
		
		final JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		final JButton chatButton = new JButton();
		chatButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				chatButtonAction(arg0);
			}
		});
		chatButton.setText("Chat");
		panel_1.add(chatButton);
		pack();
	}
	
	protected void chatButtonAction(ActionEvent arg0) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Put code for server here
				try {
					ChatClient nw = new ChatClient();
					String[] arg = {hostTF.getText(),serverPortTF.getText()}; 
					nw.main(arg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();		
	}
	
	//public static void main(String args[]) {
	//	try {
	//		FriendList frame = new FriendList();
	//		frame.setVisible(true);
	//	} catch (Exception e) {
	//		e.printStackTrace();
	//	}
	//}
}
