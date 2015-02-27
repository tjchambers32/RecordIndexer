/**
 * 
 */
package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shared.model.Field;
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

	public List<Image> getAll() throws DatabaseException {

		ArrayList<Image> result = new ArrayList<Image>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT id, projectID, filepath, availability FROM images";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int projectID = rs.getInt(2);
				String filepath = rs.getString(3);
				int availability = rs.getInt(4);

				result.add(new Image(id, projectID, filepath, availability));
			}
		} catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(),
					e);
			throw serverEx;
		} finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		return result;
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

}
