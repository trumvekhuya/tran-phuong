package common;

public class TagValue {
	private String tag;
	private String content;

	public TagValue() {
		tag = "";
		content = "";
	}

	public TagValue(String t, String c) {
		tag = t;
		content = c;
	}

	public void set(String t, String c) {
		tag = t;
		content = c;
	}

	public String getTag() {
		return tag;
	}

	public String getContent() {
		return content;
	}
}
