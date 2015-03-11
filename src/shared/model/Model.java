package shared.model;

import java.util.*;

import server.database.*;
import shared.communication.*;


public class Model {
	
	Database db = new Database();
	
	public static void initialize() throws ModelException {		

		try {
			Database.initialize();		
		}
		catch (DatabaseException e) {
			throw new ModelException(e.getMessage(), e);
		}		
	}
	
	public ValidateUser_Result validateUser(ValidateUser_Params params) throws ModelException, DatabaseException {

		User user = params.getParams();

		User returnUser = null;
		try {
			db.startTransaction();
			returnUser = db.getUserDAO().validateUser(user);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
		
		ValidateUser_Result result = new ValidateUser_Result(returnUser);
		return result;
	}
	
	public GetProjects_Result getProjects(GetProjects_Params params) throws ModelException, DatabaseException {

		User user = params.getParams();
		
		List<Project> returnProjects = null;
		try {
			db.startTransaction();
			returnProjects = db.getProjectDAO().getAll();
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
				
		GetProjects_Result result = new GetProjects_Result(returnProjects);
		return result;
	}
	
	public List<Field> getAllFields() throws ModelException, DatabaseException {	
		
		try {
			db.startTransaction();
			List<Field> fields = db.getFieldDAO().getAll();
			db.endTransaction(true);
			return fields;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	public void addField(Field field) throws ModelException, DatabaseException {
		
		try {
			db.startTransaction();
			db.getFieldDAO().add(field);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	public void updateField(Field field) throws ModelException, DatabaseException {
		
		try {
			db.startTransaction();
			db.getFieldDAO().update(field);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	public void deleteField(Field field) throws ModelException, DatabaseException {
		
		try {
			db.startTransaction();
			db.getFieldDAO().delete(field);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 */
	public void clear() {
		
		try {
			db.startTransaction();
			db.clear();
			db.endTransaction(true);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @param user
	 * @throws DatabaseException 
	 * @throws ModelException 
	 */
	public void addUser(User user) throws DatabaseException, ModelException {
		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getUserDAO().add(user);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
		
	}

	/**
	 * @param project
	 * @throws DatabaseException 
	 * @throws ModelException 
	 */
	public void addProject(Project project) throws DatabaseException, ModelException {
		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getProjectDAO().add(project);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
		
	}

	/**
	 * @param image
	 * @throws DatabaseException 
	 * @throws ModelException 
	 */
	public void addImage(Image image) throws DatabaseException, ModelException {
		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getImageDAO().add(image);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
		
	}

	/**
	 * @param record
	 * @throws DatabaseException 
	 * @throws ModelException 
	 */
	public void addRecord(Record record) throws DatabaseException, ModelException {
		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getRecordDAO().add(record);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}

	/**
	 * @param value
	 * @throws ModelException 
	 * @throws DatabaseException 
	 */
	public void addValue(Value value) throws ModelException, DatabaseException {
		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getValueDAO().add(value);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}		
	}
	
	
	
}