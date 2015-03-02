/**
 * 
 */
package server.database;

import java.io.File;
import java.sql.*;

/**
 * @author tchambs
 * 
 */
public class ServerFacade {

	private static final String DATABASE_DIRECTORY = "database";
	private static final String DATABASE_FILE = "contactmanager.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:"
			+ DATABASE_DIRECTORY + File.separator + DATABASE_FILE;

	public static void initialize() throws ServerFacadeException {
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			ServerFacadeException serverEx = new ServerFacadeException(
					"Could not load database driver", e);
			throw serverEx;
		}
	}

	private static ServerFacade db;
	private FieldDAO fieldDAO;
	private ImageDAO imageDAO;
	private ProjectDAO projectDAO;
	private RecordDAO recordDAO;
	private UserDAO userDAO;
	private ValueDAO valueDAO;
	private Connection connection;

	/**
	 * Initializes all of the DAO classes
	 */
	public ServerFacade() {
		//TODO: FIX ALL DAO classes with ServerFacade instead of Database
		fieldDAO = new FieldDAO(this);
		imageDAO = new ImageDAO(this);
		projectDAO = new ProjectDAO(this);
		recordDAO = new RecordDAO(this);
		userDAO = new UserDAO(this);
		valueDAO = new ValueDAO(this);
		connection = null;
	}

	public void startTransaction() throws ServerFacadeException {
		try {
			assert (connection == null);
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);

		} catch (SQLException e) {
			throw new ServerFacadeException(
					"Could not connect to database. Make sure " + DATABASE_FILE
							+ " is available in ./" + DATABASE_DIRECTORY, e);
		}
	}

	public void endTransaction(boolean commit) throws ServerFacadeException {
		if (connection != null) {
			try {
				if (commit) {
					connection.commit();
				} else {
					connection.rollback();
				}
			} catch (SQLException e) {
				System.out.println("Could not end transaction");
				e.printStackTrace();
			} finally {
				safeClose(connection);
				connection = null;
			}
		}
	}

	public static void safeClose(Connection conn) throws ServerFacadeException {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new ServerFacadeException("Could not close connection", e);
			}
		}
	}

	public static void safeClose(Statement stmt) throws ServerFacadeException {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new ServerFacadeException("Could not close statement", e);
			}
		}
	}

	public static void safeClose(PreparedStatement stmt)
			throws ServerFacadeException {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new ServerFacadeException(
						"Could not close prepared statement", e);
			}
		}
	}

	public static void safeClose(ResultSet rs) throws ServerFacadeException {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new ServerFacadeException("Could not close ResultSet", e);
			}
		}
	}

	public static ServerFacade getDb() {
		return db;
	}

	public static void setDb(ServerFacade db) {
		ServerFacade.db = db;
	}

	public FieldDAO getFieldDAO() {
		return fieldDAO;
	}

	public void setFieldDAO(FieldDAO fieldDAO) {
		this.fieldDAO = fieldDAO;
	}

	public ImageDAO getImageDAO() {
		return imageDAO;
	}

	public void setImageDAO(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}

	public ProjectDAO getProjectDAO() {
		return projectDAO;
	}

	public void setProjectDAO(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
	}

	public RecordDAO getRecordDAO() {
		return recordDAO;
	}

	public void setRecordDAO(RecordDAO recordDAO) {
		this.recordDAO = recordDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public ValueDAO getValueDAO() {
		return valueDAO;
	}

	public void setValueDAO(ValueDAO valueDAO) {
		this.valueDAO = valueDAO;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 
	 */
	public void clear() {

	}

}
