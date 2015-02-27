/**
 * 
 */
package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	public void add(Record record) {

	}

	public void update(Record record) {

	}

	public void delete(Record record) {

	}

	public List<Record> getAll() throws DatabaseException {

		ArrayList<Record> result = new ArrayList<Record>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT id, imageID, rowNumber, fieldNumber FROM records";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int imageID = rs.getInt(2);
				int rowNumber = rs.getInt(3);
				int fieldNumber = rs.getInt(4);

				result.add(new Record(id, imageID, rowNumber, fieldNumber));
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
