package server.facade;

import java.util.*;

import server.database.*;
import shared.communication.*;
import shared.model.*;


public class ServerFacade {

	public static void initialize() throws ServerFacadeException {		
		try {
			Database.initialize();		
		}
		catch (DatabaseException e) {
			throw new ServerFacadeException(e.getMessage(), e);
		}		
	}
	
	public ValidateUser_Result validateUser(ValidateUser_Params params) throws ServerFacadeException, DatabaseException {
		Database db = new Database();
		User user = params.getParams();

		User returnUser = null;
		try {
			db.startTransaction();
			returnUser = db.getUserDAO().validateUser(user);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerFacadeException(e.getMessage(), e);
		}
		
		ValidateUser_Result result = new ValidateUser_Result(returnUser);
		return result;
	}
	
	public GetProjects_Result getProjects(GetProjects_Params params) throws ServerFacadeException, DatabaseException {
		Database db = new Database();
		User user = params.getParams();
		
		//TODO: check if user is valid??
		
		List<Project> returnProjects = null;
		try {
			db.startTransaction();
			returnProjects = db.getProjectDAO().getAll();
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerFacadeException(e.getMessage(), e);
		}
				
		GetProjects_Result result = new GetProjects_Result(returnProjects);
		return result;
	}
	
	
	
	
	
	public static List<Field> getAllFields() throws ServerFacadeException, DatabaseException {	

		Database db = new Database();
		
		try {
			db.startTransaction();
			List<Field> fields = db.getFieldDAO().getAll();
			db.endTransaction(true);
			return fields;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerFacadeException(e.getMessage(), e);
		}
	}
	
	public static void addField(Field field) throws ServerFacadeException, DatabaseException {

		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getFieldDAO().add(field);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerFacadeException(e.getMessage(), e);
		}
	}
	
	public static void updateField(Field field) throws ServerFacadeException, DatabaseException {

		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getFieldDAO().update(field);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerFacadeException(e.getMessage(), e);
		}
	}
	
	public static void deleteField(Field field) throws ServerFacadeException, DatabaseException {

		Database db = new Database();
		
		try {
			db.startTransaction();
			db.getFieldDAO().delete(field);
			db.endTransaction(true);
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerFacadeException(e.getMessage(), e);
		}
	}

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