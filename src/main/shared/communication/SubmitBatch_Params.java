/**
 * 
 */
package main.shared.communication;

import java.util.ArrayList;

import main.shared.model.*;

/**
 * @author tchambs
 *
 */
public class SubmitBatch_Params {

	private User user;
	private ArrayList<Record> records;
	private ArrayList<Value> values;

	/**
	 * 
	 * @param user the user who is submitting the batch
	 * @param records a list of each record the user is submitting
	 */
	public SubmitBatch_Params(User user, ArrayList<Record> records, ArrayList<Value> values) {
		setUser(user);
		setRecords(records);
		setValues(values);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<Record> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<Record> records) {
		this.records = records;
	}

	public ArrayList<Value> getValues() {
		return values;
	}

	public void setValues(ArrayList<Value> values) {
		this.values = values;
	}
	
	
	
}
