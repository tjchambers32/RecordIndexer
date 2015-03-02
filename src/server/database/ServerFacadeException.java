/**
 * 
 */
package server.database;

/**
 * @author tchambs
 * 
 */
@SuppressWarnings("serial")
public class ServerFacadeException extends Exception {

	/**
	 * @param string
	 * @param e
	 */
	public ServerFacadeException(String message, Throwable cause) {
		super(message, cause);
	}

}
