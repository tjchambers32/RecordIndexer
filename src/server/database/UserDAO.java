package server.database;

import shared.model.User;

/**
 * @author tchambs
 *
 */
public class UserDAO {

	
	private Database db;
	/**
	 * @param database
	 */
	public UserDAO(Database database) {
		this.setDb(database);
	}

	public void add(User user) {
		
	}
	
	public void update(User user) {
		
	}
	
	public void delete(User user) {
		
	}
	
	public User validateUser(User user) {
		
		User result = null;
		
		
		return result;
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}
}
