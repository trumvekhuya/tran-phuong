package common;

public class ContentFormatExeption extends Exception{
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContentFormatExeption(int c){
			super("Data contain invalid character: " + (char)c);
	}
}
