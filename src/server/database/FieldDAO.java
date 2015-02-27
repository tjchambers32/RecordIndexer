/**
 * 
 */
package server.database;

import java.sql.*;
import java.util.*;

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
	
	public ArrayList<Search_Result> search(ArrayList<Integer> fields, ArrayList<String> search_values ) throws DatabaseException {
		
		ArrayList<Search_Result> result = new ArrayList<Search_Result>();	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			for (int id : fields) {
				for (String value : search_values) {
					String query = "SELECT records.imageID, image.filepath, records.rowNumber, fields.id " +
							"FROM images, records, fields " +
							"WHERE images.imageID = records.imageID " +
							"AND images.projectID = fields.projectID " + 
							"AND fields.fieldNumber = records.fieldNumber " +
							"AND fields.ID = ? " +
							"AND records.value = ? ";
					
					stmt = db.getConnection().prepareStatement(query);
					stmt.setInt(1, id);
					stmt.setString(2, value);
					rs = stmt.executeQuery();
					while(rs.next()) {
						//TODO: PICK UP HERE!!
						//return an arraylist of search_results. Each find should be a search_result
					}
				}
			}
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		return result;
	}
	
	public ArrayList<Field> getFields(int projectID) throws DatabaseException {
		
		ArrayList<Field> result = new ArrayList<Field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from fields where fields.projectID = ?";
			stmt = db.getConnection().prepareStatement(query);
			
			stmt.setInt(1, projectID);
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt(1);
				int fieldNumber = rs.getInt(2);
				String title = rs.getString(3);
				int xCoord = rs.getInt(4);
				int width = rs.getInt(5);
				String helpHTML = rs.getString(6);
				String knownData = rs.getString(7);
				int projID = rs.getInt(8);

				result.add(new Field(id, fieldNumber, title, xCoord, width, helpHTML, knownData, projID));
			}
			
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		return result;
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

	/**
	 * @return
	 * @throws DatabaseException 
	 */
	public List<Field> getAll() throws DatabaseException {
		
		ArrayList<Field> result = new ArrayList<Field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select id, fieldNumber, title, xCoord, width, helpHTML, knownData, projectID from field";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int fieldNumber = rs.getInt(2);
				String title = rs.getString(3);
				int xCoord = rs.getInt(4);
				int width = rs.getInt(5);
				String helpHTML = rs.getString(6);
				String knownData = rs.getString(7);
				int projectID = rs.getInt(8);

				result.add(new Field(id, fieldNumber, title, xCoord, width, helpHTML, knownData, projectID));
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}		
		return result;	
	}
}
