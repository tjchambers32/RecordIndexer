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
public class Database {

	private static final String DATABASE_DIRECTORY = "database";
	private static final String DATABASE_FILE = "contactmanager.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY +
												File.separator + DATABASE_FILE;
	
	public static void initialize() throws DatabaseException {
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e) {
			DatabaseException serverEx = new DatabaseException("Could not load database driver", e);
			throw serverEx; 
		}
	}
	
	private static Database db;
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
	public Database() {
		fieldDAO = new FieldDAO(this);
		imageDAO = new ImageDAO(this);
		projectDAO = new ProjectDAO(this);
		recordDAO = new RecordDAO(this);
		userDAO = new UserDAO(this);
		valueDAO = new ValueDAO(this);
		connection = null;
	}
	
	public void startTransaction() throws DatabaseException {
		try {
			assert (connection == null);			
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
			
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not connect to database. Make sure " + 
				DATABASE_FILE + " is available in ./" + DATABASE_DIRECTORY, e);
		}
	}
	
	public void endTransaction(boolean commit) throws DatabaseException {
		if (connection != null) {		
			try {
				if (commit) {
					connection.commit();
				}
				else {
					connection.rollback();
				}
			}
			catch (SQLException e) {
				System.out.println("Could not end transaction");
				e.printStackTrace();
			}
			finally {
				safeClose(connection);
				connection = null;
			}
		}
	}
	
	public static void safeClose(Connection conn) throws DatabaseException {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				throw new DatabaseException("Could not close connection", e);
			}
		}
	}
	
	public static void safeClose(Statement stmt) throws DatabaseException{
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				throw new DatabaseException("Could not close statement", e);
			}
		}
	}
	
	public static void safeClose(PreparedStatement stmt) throws DatabaseException{
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				throw new DatabaseException("Could not close prepared statement", e);
			}
		}
	}
	
	public static void safeClose(ResultSet rs) throws DatabaseException{
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				throw new DatabaseException("Could not close ResultSet", e);
			}
		}
	}
	
	public static Database getDb() {
		return db;
	}

	public static void setDb(Database db) {
		Database.db = db;
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
	
	
	
}
