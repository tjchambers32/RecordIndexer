package server.facade;

import java.util.*;

import server.database.*;
import shared.communication.*;
import shared.model.*;

public class ServerFacade {

	public static void initialize() throws ServerFacadeException {
		try {
			Database.initialize();
		} catch (DatabaseException e) {
			throw new ServerFacadeException(e.getMessage(), e);
		}
	}

	public ValidateUser_Result validateUser(ValidateUser_Params params)
			throws ServerFacadeException, DatabaseException {
		
		Database db = new Database();
		User user = params.getParams();

		User returnUser = null;
		try {
			db.startTransaction();
			returnUser = db.getUserDAO().validateUser(user);
			db.endTransaction(true);
		} catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerFacadeException(e.getMessage(), e);
		}

		ValidateUser_Result result = new ValidateUser_Result(returnUser);
		return result;
	}

	public GetProjects_Result getProjects(GetProjects_Params params)
			throws ServerFacadeException, DatabaseException {

		Database db = new Database();

		User user = params.getParams();
		// TODO: check if user is valid??

		List<Project> returnProjects = null;
		try {
			db.startTransaction();
			returnProjects = db.getProjectDAO().getAll();
			db.endTransaction(true);
		} catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerFacadeException(e.getMessage(), e);
		}

		GetProjects_Result result = new GetProjects_Result(returnProjects);
		return result;
	}

	/**
	 * @param params
	 * @return
	 * @throws DatabaseException
	 * @throws ServerFacadeException
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params)
			throws ServerFacadeException, DatabaseException {

		SubmitBatch_Result result = new SubmitBatch_Result();
		Database db = new Database();
		User user = params.getUser();
		ValidateUser_Params validate = new ValidateUser_Params(user);
		ValidateUser_Result validUser = validateUser(validate);

		if (validUser.getResult() == null) {
			result.setResult(false);
			throw new ServerFacadeException("Invalid username and/or password");
		} else {
			user = validUser.getResult();
		}
				
		db.startTransaction();
		
		Image image = db.getImageDAO().getImage(user.getImageID());
		
		if (image.getAvailability() == 1) {
			throw new ServerFacadeException("ERROR. Batch submitted previously.");
		} else {
			image.setAvailability(1);
		}
		
		int recordsPerImage = db.getImageDAO().getNumberOfRecords(user.getImageID());
		user.setImageID(-1); //user no longer assigned to image
		user.setRecordsIndexed(user.getRecordsIndexed() + recordsPerImage);
		db.getUserDAO().update(user);

		List<Record> records = params.getRecords();
		RecordDAO recordDAO = db.getRecordDAO();
		
		for (int i = 0; i < records.size(); i++) {
			recordDAO.add(records.get(i));
		}
		
		db.endTransaction(true);
		result.setResult(true);
		
		return result;
	}

	/**
	 * @param params
	 * @return
	 * @throws ServerFacadeException 
	 * @throws ModelException 
	 * @throws DatabaseException 
	 */
	public ArrayList<Search_Result> search(Search_Params params) throws ServerFacadeException, ModelException, DatabaseException {
		ArrayList<Search_Result> results = new ArrayList<Search_Result>();
		Database db = new Database();
		User user = params.getUser();
		ValidateUser_Params validate = new ValidateUser_Params(user);
		ValidateUser_Result validUser = validateUser(validate);
		if (validUser.getResult() == null)
			throw new ServerFacadeException("Invalid username and/or password");
		
		ArrayList<Integer> fieldIDs = params.getFields();
		ArrayList<String> search_values = params.getSearch_values();
		
		try {
			db.startTransaction();
			results = db.getFieldDAO().search(fieldIDs, search_values);
			db.endTransaction(true);
		} 
		catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
		
		return results;
	}
	
	public static List<Field> getAllFields() throws ServerFacadeException,
			DatabaseException {

		Database db = new Database();

		try {
			db.startTransaction();
			List<Field> fields = db.getFieldDAO().getAll();
			db.endTransaction(true);
			return fields;
		} catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerFacadeException(e.getMessage(), e);
		}
	}

	public static void addField(Field field) throws ServerFacadeException,
			DatabaseException {

		Database db = new Database();

		try {
			db.startTransaction();
			db.getFieldDAO().add(field);
			db.endTransaction(true);
		} catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerFacadeException(e.getMessage(), e);
		}
	}

	public static void updateField(Field field) throws ServerFacadeException,
			DatabaseException {

		Database db = new Database();

		try {
			db.startTransaction();
			db.getFieldDAO().update(field);
			db.endTransaction(true);
		} catch (DatabaseException e) {
			db.endTransaction(false);
			throw new ServerFacadeException(e.getMessage(), e);
		}
	}

	public static void deleteField(Field field) throws ServerFacadeException,
			DatabaseException {

		Database db = new Database();

		try {
			db.startTransaction();
			db.getFieldDAO().delete(field);
			db.endTransaction(true);
		} catch (DatabaseException e) {
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