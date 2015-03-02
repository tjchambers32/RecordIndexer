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

	private ServerFacade db;

	/**
	 * @param database
	 */
	public ProjectDAO(ServerFacade database) {
		this.setDb(database);
	}

	public void add(Project project) {

	}

	public void update(Project project) {

	}

	public void delete(Project project) {

	}

	public List<Project> getAll() throws ServerFacadeException {

		List<Project> result = new ArrayList<Project>();
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
			ServerFacadeException serverEx = new ServerFacadeException(e.getMessage(),
					e);
			throw serverEx;
		} finally {
			ServerFacade.safeClose(rs);
			ServerFacade.safeClose(stmt);
		}
		return result;
	}

	public ServerFacade getDb() {
		return db;
	}

	public void setDb(ServerFacade db) {
		this.db = db;
	}

}
