/**
 * 
 */
package server.database;

/**
 * @author tchambs
 *
 */
@SuppressWarnings("serial")
public class DatabaseException extends Exception{

	/**
	 * @param string
	 * @param e
	 */
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	
	
}
