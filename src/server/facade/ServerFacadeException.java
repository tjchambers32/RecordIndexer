package server.facade;

/**
 * @author tchambs
 * 
 */
@SuppressWarnings("serial")
public class ServerFacadeException extends Exception {

	/**
	 * @param message
	 * @param e
	 */
	public ServerFacadeException(String message, Throwable cause) {
		super(message, cause);
	}

}
