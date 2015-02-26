/**
 * 
 */
package server.database;

import shared.model.Image;

/**
 * @author tchambs
 *
 */
public class ImageDAO {

	private Database db;
	/**
	 * @param database
	 */
	public ImageDAO(Database database) {
		this.setDb(database);
	}

	public void add(Image image) {
		
	}
	
	public void update(Image image) {
		
	}
	
	public void delete(Image image) {
		
	}
	
	public Image getImage(int imageID) {
		Image result = null;
		
		return result;
	}
	
	public Image getSampleImage(int projectID) {
		
		Image result = null;
		
		return result;
	}
	
	public Image downloadBatch(int projectID) {
		
		Image result = null;
		
		return result;
	}
	
	public void SubmitBatch() {
		
	}
	
	public int getNumberOfRecords(int imageID) {
		int result = 0;
		
		return result;
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}
	
	
}
