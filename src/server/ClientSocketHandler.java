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
					InputStreamHandler ish = new InputStreamHandler(frame,is, os);
					ish.start();
					//Thread.sleep(10);
					//while(frame.isVisible()){};
					if(!frame.isVisible() || frame.isEnabled()== false) {
						is.close();
						os.close();
						ish.stopByClosingSocket(csocket);
						System.out.println("thread stop !!!!!");
						this.stopByClosingSocket();
					}
					//csocket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
