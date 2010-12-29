package common;

public class Tags {
	public static final String OPEN_CONN = "<SESSION_REQ>";
	public static final String END_CONN = "</SESSION_REQ>";
	public static final String OPEN_RES = "<SESSION_ACK>"; // chua hien thuc 2
	public static final String END_RES = "</SESSION_ACK>"; // voi cac tag lien
	public static final String OPEN_SEND = "<CHAT_MSG>";
	public static final String END_SEND = "</CHAT_MSG>";
	public static final String DISC = "<SESSION_CLOSE />";
	public static final String OPEN_FILE_CONN = "<FILE_REQ>"; //
	public static final String END_FILE_CONN = "</FILE_REQ>"; //
	public static final String OPEN_FILE_RES = "<FILE_REQ_ACK>"; //
	public static final String END_FILE_RES = "</FILE_REQ_ACK>"; // 
	public static final String FILE_BEGIN = "<FILE_DATA_BEGIN />"; //
	public static final String OPEN_FILE_DATA = "<FILE_DATA>"; //
	public static final String END_FILE_DATA = "</FILE_DATA>"; //
	public static final String FILE_END = "<FILE_DATA_END />"; //
	public static final int TAG_MAX_LENGTH = 50;
	
	public static final boolean validateTags(String start, String end) {
		boolean r = false;
		String s, e;
		s = start.toUpperCase();
		e = end.toUpperCase();
		if (s.substring(1, s.length()).equals(e.substring(2, e.length()))
				&& e.charAt(1) == '/') {
			r = true;
		}
		return r;
	}
}
