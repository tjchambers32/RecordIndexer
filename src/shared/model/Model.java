package shared.model;

import java.util.*;

import server.database.*;


public class Model {

	public static void initialize() throws ModelException {		
		try {
			Database.initialize();		
		}
		catch (DatabaseException e) {
			throw new ModelException(e.getMessage(), e);
		}		
	}
	
	public static List<Field> getAllFields() throws ModelException, DatabaseException {	

		Database db = new Database();
		
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
	
	public static void addField(Field field) throws ModelException, DatabaseException {

		Database db = new Database();
		
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
	
	public static void updateField(Field field) throws ModelException, DatabaseException {

		Database db = new Database();
		
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
	
	public static void deleteField(Field field) throws ModelException, DatabaseException {

		Database db = new Database();
		
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
	public static void clear() {
		
		Database db = new Database();
		try {
			db.startTransaction();
			db.clear();
			db.endTransaction(true);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
	}

}