/**
 * 
 */
package server.database;

import shared.model.Project;

/**
 * @author tchambs
 *
 */
public class ProjectDAO {

	private Database db;
	
	/**
	 * @param database
	 */
	public ProjectDAO(Database database) {
		this.setDb(database);
	}

	public void add(Project project) {
		
	}
	
	public void update(Project project) {
		
	}
	
	public void delete(Project project) {
		
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

}
