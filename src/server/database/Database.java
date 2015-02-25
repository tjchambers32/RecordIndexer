/**
 * 
 */
package server.database;

/**
 * @author tchambs
 *
 */
public class Database {

	private static Database db;
	
	public Database getInstance() {
		if (db == null) {
			db = new Database();
		}
		
		return db;
	}
	
}
