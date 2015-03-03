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

	public Image getImage(int imageID) throws DatabaseException {
		Image result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT id, projectID, filepath, availability FROM images WHERE id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, imageID);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt(1);
				int projectID = rs.getInt(2);
				String filepath = rs.getString(3);
				int availability = rs.getInt(4);

				result = new Image(id, projectID, filepath, availability);
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

	public Image getSampleImage(int projectID) throws DatabaseException {
		Image result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT id, projectID, filepath, availability FROM images WHERE projectID = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, projectID);
			rs = stmt.executeQuery();

			rs.next(); // just get the first one
			int id = rs.getInt(1);
			int projID = rs.getInt(2);
			String filepath = rs.getString(3);
			int availability = rs.getInt(4);

			result = new Image(id, projID, filepath, availability);

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

	public Image downloadBatch(int projectID) throws DatabaseException {
		Image result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT id, projectID, filepath, availability FROM images WHERE projectID = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, projectID);
			stmt.setInt(2, -1); // -1 = available
			rs = stmt.executeQuery();

			rs.next(); // just get the first one
			int id = rs.getInt(1);
			int projID = rs.getInt(2);
			String filepath = rs.getString(3);
			int availability = rs.getInt(4);

			result = new Image(id, projID, filepath, availability);

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

	public void SubmitBatch(Image image) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE images SET projectID = ?, filepath = ?, availability = ? WHERE id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, image.getProjectID());
			stmt.setString(2, image.getFilepath());
			stmt.setInt(3, image.getAvailability());
			stmt.setInt(4, image.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update contact");
			}
		} catch (SQLException e) {
			throw new DatabaseException("Could not update contact", e);
		} finally {
			Database.safeClose(stmt);
		}
	}

	public int getNumberOfRecords(int imageID) throws DatabaseException {
		int result = 0;

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT projects.recordsPerImage, FROM images, projects WHERE images.projectID = projects.id AND images.id = ?";

			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, imageID);

			rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
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
