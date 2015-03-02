package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shared.model.User;

/**
 * @author tchambs
 * 
 */
public class UserDAO {

	private ServerFacade db;

	/**
	 * @param database
	 */
	public UserDAO(ServerFacade database) {
		this.setDb(database);
	}

	public void add(User user) {

	}

	public void update(User user) {

	}

	public void delete(User user) {

	}

	/**
	 * 
	 * @param user
	 *            pass in a user object (contains username and password)
	 * @return user object if found, otherwise null
	 * @throws ServerFacadeException
	 */
	public User validateUser(User user) throws ServerFacadeException {
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
				int selectedImage = rs.getInt(8);

				result = new User(id, username, password, firstName, lastName,
						email, recordsIndexed, selectedImage);
			}

		} catch (Exception e) {
			throw new ServerFacadeException(e.getMessage(), e);
		} finally {
			ServerFacade.safeClose(rs);
			ServerFacade.safeClose(stmt);
		}

		return result;
	}

	/**
	 * 
	 * @return
	 * @throws ServerFacadeException
	 */
	public List<User> getAll() throws ServerFacadeException {

		ArrayList<User> result = new ArrayList<User>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT id, username, password, firstName, lastName, email, recordsIndexed, selectedImage FROM users";
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
			ServerFacadeException serverEx = new ServerFacadeException(
					e.getMessage(), e);
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
