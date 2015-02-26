/**
 * 
 */
package shared.model;

import server.database.DatabaseException;

/**
 * @author tchambs
 *
 */
public class ModelException extends Exception {

	/**
	 * @param message
	 * @param e
	 */
	public ModelException(String message, Throwable cause) {
		super(message, cause);
	}

	
	
}
