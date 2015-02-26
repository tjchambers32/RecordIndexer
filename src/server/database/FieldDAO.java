/**
 * 
 */
package server.database;

import java.util.ArrayList;

import shared.communication.Search_Result;
import shared.model.Field;

/**
 * @author tchambs
 *
 */
public class FieldDAO {

	private Database db;
	
	FieldDAO(Database database) {
		this.setDb(database);
	}
	
	public void add(Field field) {
		
	}
	
	public void update(Field field) {
		
	}
	
	public void delete(Field field) {
		
	}
	
	public Search_Result search(ArrayList<Integer> fields, ArrayList<String> search_values ) {
		Search_Result result = null;
		
		return result;
	}
	
	public ArrayList<Field> getFields(int projectID) {
		ArrayList<Field> result = null;
		
		return result;
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}
}
