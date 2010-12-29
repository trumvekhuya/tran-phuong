package fileHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Queue;

import common.BlockingQueue;
import common.Tags;


public class FileHandler extends Thread{
	private BlockingQueue<String> qe;
	private String fileName;
	private FileInputStream in;
	
	public FileHandler(String fn, BlockingQueue<String> que){
		this.qe = que;
		this.fileName = fn;
	}
	
    @Override
	public void run() {
		in = null;
		try {
			in = new FileInputStream(fileName);
			int c;
			int i = 0;
			String val = "";
			c = in.read();
			while (c != -1) {
				while (i < 20 && c!= -1) {
					val += Character.toString((char) c);
					c = in.read();
					i++;
				}
				val = Tags.OPEN_FILE_DATA + converString(val) + Tags.END_FILE_DATA;
				qe.push(val);
				Thread.sleep(5);
				i = 0;
				val = "";
			}
			in.close();
			val = Tags.FILE_END;
			qe.push(val);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}

	}
	private String converString(String mess){
	       String result = "";
	       char []arr = mess.toCharArray();
	       for(int i = 0; i < arr.length; i++){
	           result += arr[i];
	           if(arr[i] == '<' || arr[i] == '>')
	               result += arr[i];
	       }
	       return result;
	   }
}
