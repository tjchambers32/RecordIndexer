/**
 * 
 */
package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	public void add(Project project) throws DatabaseException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try {
			String query = "INSERT INTO projects"
					+ "(title, recordsPerImage, firstYCoord, recordHeight) "
					+ "VALUES (?, ?, ?, ?)";

			stmt = db.getConnection().prepareStatement(query);

			stmt.setString(1, project.getTitle());
			stmt.setInt(2, project.getRecordsPerImage());
			stmt.setInt(3, project.getFirstYCoord());
			stmt.setInt(4, project.getRecordHeight());

			if (stmt.executeUpdate() == 1) {
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1); // ID of the new User from the
											// auto-incrementing table
				project.setId(id);
			} else {
				throw new DatabaseException("Could not insert project");
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			Database.safeClose(stmt);
			Database.safeClose(keyStmt);
			Database.safeClose(keyRS);
		}
	}

	public void update(Project project) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE projects "
					+ "SET title=?, recordsPerImage=?, firstYCoord=?, recordHeight=?"
					+ "WHERE id=?";
			stmt = db.getConnection().prepareStatement(query);

			stmt.setString(1, project.getTitle());
			stmt.setInt(2, project.getRecordsPerImage());
			stmt.setInt(3, project.getFirstYCoord());
			stmt.setInt(4, project.getRecordHeight());
			stmt.setInt(5, project.getId());

			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update project");
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			Database.safeClose(stmt);
		}
	}

	public void delete(Project project) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "DELETE FROM projects " + "WHERE id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, project.getId());

			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete project");
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			Database.safeClose(stmt);
		}
	}

	public List<Project> getAll() throws DatabaseException {

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

	/**
	 * @param projectID 
	 * @return
	 * @throws DatabaseException
	 */
	public Project getProject(int projectID) throws DatabaseException {
		Project result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT id, title, recordsPerImage, firstYCoord, recordHeight FROM projects "
						+ "WHERE id = ?";
			
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, projectID);
			
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int recordsPerImage = rs.getInt(3);
				int firstYCoord = rs.getInt(4);
				int recordHeight = rs.getInt(5);

				result = new Project(id, title, recordsPerImage, firstYCoord,
						recordHeight);
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

}
