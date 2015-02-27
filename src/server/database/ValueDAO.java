/**
 * 
 */
package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			String query = "SELECT id, recordID, value FROM values";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int recordID = rs.getInt(2);
				String value = rs.getString(3);

				result.add(new Value(id, recordID, value));
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
