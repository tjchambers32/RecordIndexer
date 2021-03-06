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

import shared.model.Record;

/**
 * @author tchambs
 * 
 */
public class RecordDAO {

	private Database db;

	/**
	 * @param database
	 */
	public RecordDAO(Database database) {
		this.setDb(database);
	}

	public void add(Record record) throws DatabaseException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try {
			String query = "INSERT INTO records"
					+ "(imageID, rowNumber) "
					+ "VALUES (?, ?)";

			stmt = db.getConnection().prepareStatement(query);

			stmt.setInt(1, record.getImageID());
			stmt.setInt(2, record.getRowNumber());

			if (stmt.executeUpdate() == 1) {
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1); // ID of the new Record from the
											// auto-incrementing table
				record.setId(id);
			} else {
				throw new DatabaseException("Could not insert record");
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			Database.safeClose(stmt);
			Database.safeClose(keyStmt);
			Database.safeClose(keyRS);
		}
	}

	public void update(Record record) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE records "
					+ "SET imageID=?, rowNumber=? "
					+ "WHERE id=?";
			stmt = db.getConnection().prepareStatement(query);

			stmt.setInt(1, record.getImageID());
			stmt.setInt(2, record.getRowNumber());
			stmt.setInt(3, record.getId());

			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update record");
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			Database.safeClose(stmt);
		}
	}

	public void delete(Record record) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "DELETE FROM records " + "WHERE id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, record.getId());

			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete record");
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			Database.safeClose(stmt);
		}
	}

	public List<Record> getAll() throws DatabaseException {

		ArrayList<Record> result = new ArrayList<Record>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT id, imageID, rowNumber FROM records";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int imageID = rs.getInt(2);
				int rowNumber = rs.getInt(3);

				result.add(new Record(id, imageID, rowNumber));
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
