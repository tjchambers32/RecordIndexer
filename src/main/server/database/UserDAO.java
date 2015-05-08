package main.server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.shared.model.User;

/**
 * @author tchambs
 * 
 */
public class UserDAO {

	private Database db;

	/**
	 * @param database
	 */
	public UserDAO(Database database) {
		this.setDb(database);
	}

	public void add(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try {
			String query = "INSERT INTO users"
					+ "(username, password, firstName, lastName, email, recordsIndexed, imageID) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

			stmt = db.getConnection().prepareStatement(query);

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getRecordsIndexed());
			stmt.setInt(7, user.getImageID());

			if (stmt.executeUpdate() == 1) {
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1); // ID of the new User from the
											// auto-incrementing table
				user.setId(id);
			} else {
				throw new DatabaseException("Could not insert user");
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			Database.safeClose(stmt);
			Database.safeClose(keyStmt);
			Database.safeClose(keyRS);
		}
	}

	public void update(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE users "
					+ "SET username=?, password=?, firstName=?, lastName=?, "
					+ "email=?, recordsIndexed=?, imageID=? " + "WHERE id=?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getRecordsIndexed());
			stmt.setInt(7, user.getImageID());
			stmt.setInt(8, user.getId());

			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update user");
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			Database.safeClose(stmt);
		}
	}

	public void delete(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "DELETE FROM users " + "WHERE id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, user.getId());

			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete user");
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			Database.safeClose(stmt);
		}
	}

	/**
	 * 
	 * @param user
	 *            pass in a user object (contains username and password)
	 * @return user object if found, otherwise null
	 * @throws DatabaseException
	 */
	public User validateUser(User user) throws DatabaseException {
		User result = null;

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			String query = "SELECT * FROM users WHERE username = ? AND password = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			rs = stmt.executeQuery();

			
			while (rs.next()) {
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String firstName = rs.getString(4);
				String lastName = rs.getString(5);
				String email = rs.getString(6);
				int recordsIndexed = rs.getInt(7);
				int imageID = rs.getInt(8);

				result = new User(id, username, password, firstName, lastName,
						email, recordsIndexed, imageID);
			}
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		return result;
	}

	/**
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public List<User> getAll() throws DatabaseException {

		ArrayList<User> result = new ArrayList<User>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT id, username, password, firstName, lastName, email, recordsIndexed, imageID FROM users";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String firstName = rs.getString(4);
				String lastName = rs.getString(5);
				String email = rs.getString(6);
				int recordsIndexed = rs.getInt(7);
				int selectedImage = rs.getInt(8);

				result.add(new User(id, username, password, firstName,
						lastName, email, recordsIndexed, selectedImage));
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
