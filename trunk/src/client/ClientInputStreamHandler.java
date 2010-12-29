package client;

import gui.ChatWindow;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import common.ContentFormatExeption;
import common.TagFormatException;
import common.TagValue;
import common.Tags;

public class ClientInputStreamHandler extends Thread {
	private BufferedReader reader;
	private PrintWriter writer;
	private String serverID = "";
	private String fileName = "";
	private String fileData = "";
	private ChatWindow chatWin;
	private boolean running = false;
	private FileOutputStream out = null;
	
	public enum State {START, CHAT, FILE_REQ, FILE_DATA};
	private State state;
	
	public ClientInputStreamHandler(ChatWindow cw, InputStream is, OutputStream os){
		chatWin = cw;
		reader = new BufferedReader(new InputStreamReader(is));
		writer = new PrintWriter(new OutputStreamWriter(os));
		state = State.START;
		writer.print(Tags.OPEN_CONN + "Van Phuong" + Tags.END_CONN);
		writer.flush();
	}
	public void run(){
		running = true;
		try{
			int c = reader.read();
			while (c != -1 && running == true){
				TagValue tv;
				
				try {
					
					tv = this.getTagValue(c);
					
					if (tv.getTag().equals(Tags.DISC)){
						writer.print(Tags.DISC);
						System.out.println("Send: " + Tags.DISC);
						writer.flush();
						reader.close();
						writer.close();
						return;
					}
					
					switch(state){
						case START:
							if(tv.getTag().equals(Tags.OPEN_RES)){
								serverID = tv.getContent();
								//writer.print(Tags.OPEN_RES + userID + Tags.END_RES);
								state = State.CHAT;
								writer.print(Tags.OPEN_SEND + "Hi " + serverID + Tags.END_SEND);
								System.out.println("Send: " + Tags.OPEN_SEND + "Hi " + serverID + Tags.END_SEND);
								writer.flush();
							}
							else{
								System.out.println("Not expected!!!");
							}
							break;
						case CHAT:
							if(tv.getTag().equals(Tags.OPEN_SEND)){
								chatWin.display("Received: " + tv.getContent());
							}
							else if(tv.getTag().equals(Tags.OPEN_FILE_CONN)){
								fileName = tv.getContent();
								writer.print(Tags.OPEN_FILE_RES + fileName + Tags.END_FILE_RES);
								writer.print(Tags.OPEN_SEND + "I'm receiving: " + fileName + Tags.END_SEND);
								System.out.println("Receiving file ");
								writer.flush();
								state = State.FILE_REQ;
							}
							else if(tv.getTag().equals(Tags.OPEN_FILE_RES)){
								writer.print(Tags.FILE_BEGIN);
								writer.flush();
								state = State.CHAT;
							}
							else{
								System.out.println("Not expected!!!");
							}
							break;
						case FILE_REQ:
							if(tv.getTag().equals(Tags.OPEN_SEND)){
								chatWin.display("Received: " + tv.getContent());
							}
							else if(tv.getTag().equals(Tags.FILE_BEGIN)){
								out = new FileOutputStream("d:/dst.txt");
								state = State.FILE_DATA;
								System.out.println("open file des");
							}
							else{
								System.out.println("Not expected!!!");
							}
							break;
						case FILE_DATA:
							if(tv.getTag().equals(Tags.OPEN_SEND)){
								chatWin.display("Received: " + tv.getContent());
							}
							else if(tv.getTag().equals(Tags.OPEN_FILE_DATA)){
								fileData = tv.getContent();
								out.write(fileData.getBytes());
							}
							else if(tv.getTag().equals(Tags.FILE_END)){
								out.close();
								state = State.CHAT;
							}
							else{
								System.out.println("Not expected!!!");
							}
							break;
					}
				} catch (TagFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					writer.print(Tags.OPEN_SEND + "Malformed message received" + Tags.END_SEND);
					writer.flush();
					System.out.println("Send: " + Tags.OPEN_SEND + "Malformed message received" + Tags.END_SEND);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c = reader.read();
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
			running = false;
		}
	}
	
	public void stopByClosingSocket(Socket s){
		running = false;
		try{
			s.close();
		}catch(IOException e){}
	}
	
	public TagValue getTagValue (int preread) throws IOException, TagFormatException, ContentFormatExeption{
		TagValue tv = new TagValue();
		int c = preread;
		while (Character.isWhitespace(c)){
			c = reader.read();
		}
		String tag;
		if (c == '<'){
			tag = this.getTag();
			//if this is a disconnect tag, return
			if (tag.toUpperCase().equals(Tags.DISC)){
				tv = new TagValue(Tags.DISC, "");
			}else if(tag.toUpperCase().equals(Tags.FILE_BEGIN)){
				tv = new TagValue(Tags.FILE_BEGIN, "");
			}else if(tag.toUpperCase().equals(Tags.FILE_END)){
				tv = new TagValue(Tags.FILE_END, "");
			}
			else{
				// if this is not a disconnect tag
				String val = this.getValue();
				String endtag = this.getEndTag();
				if (Tags.validateTags(tag, endtag)){
					tv = new TagValue (tag, val);
				}else{
					TagFormatException tfe = new TagFormatException("End tag doesn't match start tag: " + tag + ":" + endtag);
					throw tfe;
				}
			}
		}else{
			TagFormatException tfe = new TagFormatException("An open tag is expected instead of --" + Character.toString((char)c) +"--");
			throw tfe;			
		}
		return tv;
	}
	public String getTag() throws IOException {
		String tag = "<";
		int c = reader.read();
		while (c != '>'){
			tag += Character.toString((char)c);
			c = reader.read();
		}
		tag += ">";
		return tag.toUpperCase();
	}
	public String getEndTag() throws IOException {
		String tag = "</";
		int c = reader.read();
		while (c != '>'){
			tag += Character.toString((char)c);
			c = reader.read();
		}
		tag += ">";
		return tag.toUpperCase();
	}
	public String getValue () throws IOException, ContentFormatExeption {
		String val = "";
		int c = reader.read();
		int next;
		while (true){
			next = reader.read();
			if(c == '<'){
				if(next == '<') {
					val += Character.toString((char) c);
					c = reader.read();
				}
				else if(next == '/') break;
					else throw new ContentFormatExeption(c);
			}
			else if(c == '>'){
				if(next == '>'){
					val += Character.toString((char) c);
					c = reader.read();
				}
				else throw new ContentFormatExeption(c);
			}
			else {
				val += Character.toString((char) c);
				c = next;
			}
		}
		return val;
	}
}
