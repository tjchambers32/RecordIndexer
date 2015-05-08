/**
 * 
 */
package main.java.shared.communication;

import java.util.ArrayList;

import main.java.shared.model.User;

/**
 * @author tchambs
 *
 */
public class Search_Params {

	private User user;
	private ArrayList<Integer> fields;
	private ArrayList<String> search_values;
	
	/**
	 * 
	 * @param user the user performing the search
	 * @param fields the fields that are being searched in
	 * @param search_values the values being searched for
	 */
	public Search_Params(User user, ArrayList<Integer> fields, ArrayList<String> search_values) {
		setUser(user);
		setFields(fields);
		setSearch_values(search_values);
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ArrayList<Integer> getFields() {
		return fields;
	}
	public void setFields(ArrayList<Integer> fields) {
		this.fields = fields;
	}
	public ArrayList<String> getSearch_values() {
		return search_values;
	}
	public void setSearch_values(ArrayList<String> search_values) {
		this.search_values = search_values;
	}
	
	
}
