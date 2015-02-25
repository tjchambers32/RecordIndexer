/**
 * 
 */
package shared.communication;

import java.util.ArrayList;
import shared.model.Field;


/**
 * @author tchambs
 *
 */
public class GetFields_Result {

	private ArrayList<Field> fields;

	/**
	 * 
	 * @param fields a list of each field
	 */
	GetFields_Result(ArrayList<Field> fields) {
		setFields(fields);
	}
	
	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}
	
}
