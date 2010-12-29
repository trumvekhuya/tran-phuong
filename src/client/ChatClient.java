package client;

import gui.ChatWindow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
	public void main(String args[]) {
		String host = "localhost";
		int serverport = 2222;
		if (args.length == 2) {
			host = args[0];
			serverport = Integer.parseInt(args[1]);
		}
		try {
			System.out.println("Connect to server on port" + serverport);
			Socket s = new Socket(host, serverport);
			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();

			try {
				ChatWindow frame = new ChatWindow("client", is, os);
				frame.setVisible(true);

				ClientInputStreamHandler cish = new ClientInputStreamHandler("client", frame, is, os);
				cish.start();

				while (!frame.isVisible() || !frame.isEnabled()) {
					is.close();
					os.close();
					cish.stopByClosingSocket(s);
					System.out.println("thread stop !!!!!");
				}
			} catch (Exception e) {
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
}
