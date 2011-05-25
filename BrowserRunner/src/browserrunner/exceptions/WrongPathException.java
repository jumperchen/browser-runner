package browserrunner.exceptions;


public class WrongPathException extends RuntimeException{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String path;
	private String message;
	public WrongPathException(String path,String message){
		this.path = path;
		this.message = message;
	}

	public String getPath(){
		return path;
	}

	public String getMessage(){
		return message;
	}
}
