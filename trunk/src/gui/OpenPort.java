package gui;

import server.ChatServer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class OpenPort extends JFrame{
	static final long serialVersionUID = 2;
	private JTextField serverPortTF;
	public OpenPort(){
		super();
		setTitle("Open Port");
		setBounds(0, 0, 250, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		serverPortTF = new JTextField();
		serverPortTF.setText("2222");
		serverPortTF.setColumns(5);
		panel.add(serverPortTF);
		
		final JButton sendButton = new JButton();
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				connectButtonAction(arg0);
			}
		});
		sendButton.setText("Open Port");
		panel.add(sendButton);
		//pack();
	}
	
	protected void connectButtonAction(ActionEvent arg0) {
		// TODO Auto-generated method stub
		ChatServer nw = new ChatServer();
		String[] arg = {serverPortTF.getText()}; 
		try {
			nw.main(arg);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setVisible(false);
		try {
			FriendList frame = new FriendList();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		try {
			try {
			    // Set System L&F
		        UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		    } 
		   catch (UnsupportedLookAndFeelException e) {
		       // handle exception
		    }
		    catch (ClassNotFoundException e) {
		       // handle exception
		    }
		    catch (InstantiationException e) {
		       // handle exception
		    }
		    catch (IllegalAccessException e) {
		       // handle exception
		    }
			OpenPort frame = new OpenPort();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
