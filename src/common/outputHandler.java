package common;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.OutputStreamWriter;
//import java.io.PrintWriter;

public class outputHandler extends Thread {

	BlockingQueue<String> que;
	private OutputStreamWriter writer;
	private Boolean running = false;

	public outputHandler(BlockingQueue<String> qe, OutputStream os) throws UnsupportedEncodingException {
            this.que = qe;
            writer = new OutputStreamWriter(os, "UTF8");
	}

    @Override
	public void run() {
		// TODO
		running = true;
		try{
			while (running) {
				try{
					String a = (String) que.pop();
					System.out.println(a);
					writer.write(a);
					writer.flush();
				}
				catch (Exception e){
					System.out.println("close output handler");
				}
			}
		} 		finally{
						try {
							writer.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
						}
		}
	}
	
	public void stopByClosing(){
		try{
			this.writer.close();
			running = false;
		}catch(Exception e){
			
		}
	}
}
