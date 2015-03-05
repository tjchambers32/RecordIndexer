package servertester.controllers;

import java.util.*;

import client.ClientException;
import client.communication.ClientCommunicator;
import servertester.views.*;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import shared.model.User;

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
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
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
		ClientCommunicator communicator = new ClientCommunicator(_view.getHost(), Integer.parseInt(_view.getPort()));
		
		String[] paramValues = getView().getParameterValues();
		User user = new User(paramValues[0], paramValues[1]);

		ValidateUser_Params params = new ValidateUser_Params(user);
		
		// Print out Results
		ValidateUser_Result result = null;
		try {
			result = communicator.ValidateUser(params);
		} 
		catch (ClientException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			_view.setResponse("FAILED\n");
			return;
		}
		
		if (result.getResult() == null) {
			_view.setResponse("FALSE\n");
		} 
		else {
			_view.setResponse(result.toString());
		}
	}
	
	private void getProjects() {
	}
	
	private void getSampleImage() {
	}
	
	private void downloadBatch() {
	}
	
	private void getFields() {
	}
	
	private void submitBatch() {
	}
	
	private void search() {
	}

}

