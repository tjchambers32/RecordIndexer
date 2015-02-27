/**
 * 
 */
package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public List<Project> getAll() throws DatabaseException {

		ArrayList<Project> result = new ArrayList<Project>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT id, title, recordsPerImage, firstYCoord, recordHeight FROM projects";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int recordsPerImage = rs.getInt(3);
				int firstYCoord = rs.getInt(4);
				int recordHeight = rs.getInt(5);

				result.add(new Project(id, title, recordsPerImage, firstYCoord,
						recordHeight));
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
