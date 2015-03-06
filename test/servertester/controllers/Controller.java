package servertester.controllers;

import java.util.*;

import client.ClientException;
import client.communication.ClientCommunicator;
import servertester.views.*;
import shared.communication.*;
import shared.model.*;

public class Controller implements IController {

	private IView _view;

	public Controller() {
		return;
	}

	public IView getView() {
		return _view;
	}

	public void setView(IView value) {
		_view = value;
	}

	// IController methods
	//

	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");

		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}

		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(
				paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}

	private void validateUser() {
		ClientCommunicator communicator = new ClientCommunicator(
				_view.getHost(), Integer.parseInt(_view.getPort()));

		String[] paramValues = getView().getParameterValues();
		User user = new User(paramValues[0], paramValues[1]);

		ValidateUser_Params params = new ValidateUser_Params(user);

		// Print out Results
		ValidateUser_Result result = null;
		try {
			result = communicator.ValidateUser(params);
		} catch (ClientException e) {
			_view.setResponse("FAILED\n");
			return;
		}

		if (result.getResult() == null) {
			_view.setResponse("FALSE\n");
		} else {
			_view.setResponse(result.toString());
		}
	}

	private void getProjects() {
		ClientCommunicator communicator = new ClientCommunicator(
				_view.getHost(), Integer.parseInt(_view.getPort()));

		String[] paramValues = getView().getParameterValues();
		User user = new User(paramValues[0], paramValues[1]);

		GetProjects_Params params = new GetProjects_Params(user);

		// Print out Results
		GetProjects_Result result = null;
		try {
			result = communicator.getProjects(params);
		} catch (ClientException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			_view.setResponse("FAILED\n");
			return;
		}

		if (result.getProjects() == null) {
			_view.setResponse("FAILED\n");
		} else {
			_view.setResponse(result.toString());
		}
	}

	private void getSampleImage() {
		ClientCommunicator communicator = new ClientCommunicator(
				_view.getHost(), Integer.parseInt(_view.getPort()));

		String[] paramValues = getView().getParameterValues();
		User user = new User(paramValues[0], paramValues[1]);

		GetSampleImage_Params params = new GetSampleImage_Params(user,
				Integer.parseInt(paramValues[2]));

		// Print out Results
		GetSampleImage_Result result = null;
		try {
			result = communicator.getSampleImage(params);
		} catch (ClientException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			_view.setResponse("FAILED\n");
			return;
		}

		if (result.getImageURL() == null) {
			_view.setResponse("FAILED\n");
		} else {
			_view.setResponse(result.toString());
		}
	}

	private void downloadBatch() {
		ClientCommunicator communicator = new ClientCommunicator(
				_view.getHost(), Integer.parseInt(_view.getPort()));

		String[] paramValues = getView().getParameterValues();
		User user = new User(paramValues[0], paramValues[1]);

		DownloadBatch_Params params = new DownloadBatch_Params(user,
				Integer.parseInt(paramValues[2]));

		// Print out Results
		DownloadBatch_Result result = null;
		try {
			result = communicator.downloadBatch(params);
		} catch (ClientException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			_view.setResponse("FAILED\n");
			return;
		}

		if (result == null) {
			_view.setResponse("FAILED\n");
		} else {
			_view.setResponse(result.toString("http://" + _view.getHost() + ":"
					+ _view.getPort() + "/"));
		}
	}

	private void getFields() {
		ClientCommunicator communicator = new ClientCommunicator(
				_view.getHost(), Integer.parseInt(_view.getPort()));

		String[] paramValues = getView().getParameterValues();
		User user = new User(paramValues[0], paramValues[1]);
		int projectID = -1; // default for no projectID passed in
		if (!paramValues[2].equals(""))
			projectID = Integer.parseInt(paramValues[2]);

		GetFields_Params params = new GetFields_Params(user, projectID);

		// Print out Results
		GetFields_Result result = null;
		try {
			result = communicator.getFields(params);
		} catch (ClientException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			_view.setResponse("FAILED\n");
			return;
		}

		if (result == null) {
			_view.setResponse("FAILED\n");
		} else {
			_view.setResponse(result.toString());
		}
	}

	private void submitBatch() {
		ClientCommunicator communicator = new ClientCommunicator(
				_view.getHost(), Integer.parseInt(_view.getPort()));

		String[] paramValues = getView().getParameterValues();
		User user = new User(paramValues[0], paramValues[1]);
		user.setImageID(Integer.parseInt(paramValues[2]));

		ArrayList<Record> records = new ArrayList<Record>();
		ArrayList<Value> valueList = new ArrayList<Value>();

		String[] recordString = paramValues[3].split("\\s*(;)\\s*");
		int rowNumber = 0;
		for (String field : recordString) {
			rowNumber++;
			String[] values = field.split("\\s*(,)\\s*");
			records.add(new Record(user.getImageID(), rowNumber));
			int valueNumber = 0;
			for (String valueString : values) {
				valueNumber++;
				valueList.add(new Value(rowNumber, valueString, valueNumber));
			}
		}

		SubmitBatch_Params params = new SubmitBatch_Params(user, records,
				valueList);

		// Print out Results
		SubmitBatch_Result result = null;
		try {
			result = communicator.submitBatch(params);
		} catch (ClientException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			_view.setResponse("FAILED\n");
			return;
		}

		if (result.getResult() == false) {
			_view.setResponse("FAILED\n");
		} else {
			_view.setResponse(result.toString());
		}
	}

	private void search() {
		ClientCommunicator communicator = new ClientCommunicator(
				_view.getHost(), Integer.parseInt(_view.getPort()));

		String[] paramValues = getView().getParameterValues();
		User user = new User(paramValues[0], paramValues[1]);

		ArrayList<Integer> fields = new ArrayList<Integer>();
		ArrayList<String> searchValues = new ArrayList<String>();

		String[] fieldString = paramValues[2].split("\\s*(,)\\s*");
		String[] valueString = paramValues[3].split("\\s*(,)\\s*");

		for (String s : fieldString) {
			fields.add(Integer.parseInt(s));
		}
		for (String s : valueString) {
			searchValues.add(s);
		}

		Search_Params params = new Search_Params(user, fields, searchValues);

		// Print out Results
		ArrayList<Search_Result> result = null;
		try {
			result = communicator.search(params);
		} catch (ClientException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			_view.setResponse("FAILED\n");
			return;
		}

		if (result == null || result.size() == 0) {
			_view.setResponse("FAILED\n");
			return;
		}
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < result.size(); i++) {
			sb.append(result.get(i).toString());
		}
		
		_view.setResponse(sb.toString());

	}
}
