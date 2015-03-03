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

import shared.model.Value;

/**
 * @author tchambs
 * 
 */
public class ValueDAO {

	private Database db;

	/**
	 * @param database
	 */
	public ValueDAO(Database database) {
		this.setDb(database);
	}

	public List<Value> getAll() throws DatabaseException {

		ArrayList<Value> result = new ArrayList<Value>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT id, recordID, value, fieldID FROM value";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int recordID = rs.getInt(2);
				String value = rs.getString(3);
				int fieldID = rs.getInt(4);


				result.add(new Value(id, recordID, value, fieldID));
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
	 * @param value
	 * @throws DatabaseException
	 */
	public void add(Value value) throws DatabaseException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try {
			String query = "INSERT INTO value"
					+ "(recordID, text, fieldID) "
					+ "VALUES (?, ?, ?)";

			stmt = db.getConnection().prepareStatement(query);

			stmt.setInt(1, value.getRecordID());
			stmt.setString(2, value.getText());
			stmt.setInt(3, value.getFieldID());

			if (stmt.executeUpdate() == 1) {
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1); // ID of the new User from the
											// auto-incrementing table
				value.setId(id);
			} else {
				throw new DatabaseException("Could not insert value");
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			Database.safeClose(stmt);
			Database.safeClose(keyStmt);
			Database.safeClose(keyRS);
		}
	}

}
