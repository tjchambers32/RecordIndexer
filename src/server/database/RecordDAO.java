/**
 * 
 */
package server.database;

import shared.model.Record;
/**
 * @author tchambs
 *
 */
public class RecordDAO {

	private Database db;
	/**
	 * @param database
	 */
	public RecordDAO(Database database) {
		this.setDb(database);
	}

	public void add(Record record) {
		
	}
	
	public void update(Record record) {
		
	}
	
	public void delete(Record record) {
		
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

}
