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
		ValidateUser_Params vParams = new ValidateUser_Params(user);
		ValidateUser_Result vResult = validateUser(vParams);
		if (vResult.getResult() == null)
			throw new ServerFacadeException("Invalid username and/or password");

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
			db.endTransaction(false);
			throw new ServerFacadeException(
					"ERROR. Batch submitted previously.");
		} else {
			image.setAvailability(1);
			db.getImageDAO().update(image);
		}

		int recordsPerImage = db.getImageDAO().getNumberOfRecords(
				user.getImageID());
		user.setImageID(-1); // user no longer assigned to image
		user.setRecordsIndexed(user.getRecordsIndexed() + recordsPerImage);
		db.getUserDAO().update(user);

		List<Record> records = params.getRecords();
		List<Value> values = params.getValues();

		for (int i = 0; i < records.size(); i++) {
			db.getRecordDAO().add(records.get(i));
		}
		for (int i = 0; i < values.size(); i++) {
			db.getValueDAO().add(values.get(i));
		}

		db.endTransaction(true);
		result.setResult(true);

		return result;
	}

	/**
	 * @param params
	 * @return
	 * @throws ServerFacadeException
	 * @throws DatabaseException
	 */
	public GetSampleImage_Result GetSampleImage(GetSampleImage_Params params)
			throws ServerFacadeException, DatabaseException {
		GetSampleImage_Result result = new GetSampleImage_Result(null);

		Database db = new Database();
		User user = params.getUser();

		ValidateUser_Params validate = new ValidateUser_Params(user);
		ValidateUser_Result validUser = validateUser(validate);

		if (validUser.getResult() == null) {
			throw new ServerFacadeException("Invalid username and/or password");
		} else {
			user = validUser.getResult();
		}

		try {
			db.startTransaction();
			Project project = db.getProjectDAO().getProject(
					params.getProjectID());
			if (project == null) {
				db.endTransaction(false);
				throw new ServerFacadeException("Invalid ProjectID");
			}
			Image sampleImage = db.getImageDAO().getSampleImage(
					params.getProjectID());
			db.endTransaction(true);
			result.setImageURL(sampleImage.getFilepath());

		} catch (DatabaseException e) {
			db.endTransaction(false);
			throw new DatabaseException(e.getMessage(), e);
		}

		return result;
	}

	/**
	 * @param params
	 * @return
	 * @throws ServerFacadeException
	 * @throws DatabaseException
	 */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params)
			throws ServerFacadeException, DatabaseException {
		DownloadBatch_Result result = null;

		Database db = new Database();
		User user = params.getUser();

		ValidateUser_Params validate = new ValidateUser_Params(user);
		ValidateUser_Result validUser = validateUser(validate);

		if (validUser.getResult() == null) {
			throw new ServerFacadeException("Invalid username and/or password");
		} else if (validUser.getResult().getImageID() != -1) {
			throw new ServerFacadeException(
					"User can only have one batch checked out at a time");
		} else {
			user = validUser.getResult();
		}

		Image userImage = null;

		Project project = null;
		ArrayList<Field> fields = null;

		try {
			db.startTransaction();
			Project p = db.getProjectDAO().getProject(params.getProjectID());
			if (p == null) {
				db.endTransaction(false);
				throw new ServerFacadeException("Invalid ProjectID");
			}
			userImage = db.getImageDAO().downloadBatch(params.getProjectID());
			if (userImage.getProjectID() == -1) {
				db.endTransaction(false);
				throw new ServerFacadeException(
						"This project has no available batches");
			}
			else
				user.setImageID(userImage.getId());

			userImage.setAvailability(0);
			db.getImageDAO().update(userImage);
			db.getUserDAO().update(user);

			project = db.getProjectDAO().getProject(params.getProjectID());

			fields = db.getFieldDAO().getFields(params.getProjectID());

			db.endTransaction(true);

		} catch (DatabaseException e) {
			db.endTransaction(false);
			throw new DatabaseException(e.getMessage(), e);
		}

		result = new DownloadBatch_Result(project, fields, userImage);
		return result;
	}

	/**
	 * @param params
	 * @return
	 * @throws DatabaseException
	 * @throws ServerFacadeException
	 */
	public GetFields_Result getFields(GetFields_Params params)
			throws ServerFacadeException, DatabaseException {

		Database db = new Database();
		User user = params.getUser();

		ValidateUser_Params validate = new ValidateUser_Params(user);
		ValidateUser_Result validUser = validateUser(validate);

		if (validUser.getResult() == null) {
			throw new ServerFacadeException("Invalid username and/or password");
		} else {
			user = validUser.getResult();
		}

		List<Field> fields = new ArrayList<Field>();
		Project p = null;

		try {
			db.startTransaction();

			if (params.getProjectID() == -1) {
				fields = db.getFieldDAO().getAll();
			} else {
				p = db.getProjectDAO().getProject(params.getProjectID());
				if (p == null) {
					db.endTransaction(false);
					throw new ServerFacadeException("Invalid ProjectID");
				} else
					fields = db.getFieldDAO().getFields(params.getProjectID());
			}
			db.endTransaction(true);

		} catch (DatabaseException e) {
			db.endTransaction(false);
			throw new DatabaseException(e.getMessage(), e);
		}
		GetFields_Result result = new GetFields_Result(fields);

		return result;
	}

	/**
	 * @param params
	 * @return
	 * @throws ServerFacadeException
	 * @throws ModelException
	 * @throws DatabaseException
	 */
	public ArrayList<Search_Result> search(Search_Params params)
			throws ServerFacadeException, ModelException, DatabaseException {
		
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
		} catch (DatabaseException e) {
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