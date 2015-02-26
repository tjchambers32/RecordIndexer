/**
 * 
 */
package server.database;

/**
 * @author tchambs
 *
 */
public class ValueDAO {

	private Database db;
	/**
	 * @param database
	 */
	public ValueDAO(Database database) {
		this.setDb(database);
	}
	
	public Database getDb() {
		return db;
	}
	
	public void setDb(Database db) {
		this.db = db;
	}

}
