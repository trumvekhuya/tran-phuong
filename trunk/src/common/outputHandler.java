package common;

//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Queue;
import java.io.OutputStreamWriter;
//import java.io.PrintWriter;

public class outputHandler extends Thread {

	//private FileOutputStream out = null;
	//OutputStream out;
	Queue<String> que;
	private PrintWriter writer;
	private Boolean running = false;

	public outputHandler(Queue<String> qe, OutputStream os) {
		this.que = qe;
		writer = new PrintWriter(new OutputStreamWriter(os));
		
	}

	public void run() {
		// TODO
		running = true;
		try{
			while (running) {
				try{
					if (!que.isEmpty()) {
						String a = (String) que.poll();
						System.out.println(a);
						writer.print(a);
						writer.flush();
					}
					//i++;
				}
				catch (Exception e){
				}
				Thread.sleep(2);
				//System.out.println("aaa");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			writer.close();
		}
	}
	
	public void stopByClosing() throws Exception{
		this.writer.close();
		running = false;
	}
}
