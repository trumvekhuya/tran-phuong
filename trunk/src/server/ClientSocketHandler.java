package server;

import gui.ChatWindow;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocketHandler extends Thread {
	private Socket csocket;
	private boolean running = false;
	public ClientSocketHandler(Socket s){
		csocket = s;
	}
	public void run(){
		running = true;
		try {
			InputStream is = csocket.getInputStream();
			OutputStream os = csocket.getOutputStream();

			try {
				if(running == true){
					ChatWindow frame = new ChatWindow("server", is, os);
					frame.setVisible(true);
					InputStreamHandler ish = new InputStreamHandler("server", frame,is, os);
					ish.start();
					if(!frame.isVisible() || frame.isEnabled()== false) {
						is.close();
						os.close();
						ish.stopByClosingSocket(csocket);
						System.out.println("thread stop !!!!!");
						this.stopByClosingSocket();
					}
				}
			} catch (Exception e) {
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			running = false;
		}
	}

	public void stopByClosingSocket(){
		running = false;
		try{
			csocket.close();
		}catch(IOException e){}
	}
}
