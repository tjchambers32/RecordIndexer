/**
 * 
 */
package shared.communication;

import java.util.ArrayList;

import shared.model.Record;
import shared.model.User;

/**
 * @author tchambs
 *
 */
public class SubmitBatch_Params {

	private User user;
	private ArrayList<Record> records;

	/**
	 * 
	 * @param user the user who is submitting the batch
	 * @param records a list of each record the user is submitting
	 */
	SubmitBatch_Params(User user, ArrayList<Record> records) {
		setUser(user);
		setRecords(records);
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
	
	
	
}
