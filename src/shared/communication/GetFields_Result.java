/**
 * 
 */
package shared.communication;

import java.util.ArrayList;
import java.util.List;

import shared.model.Field;

/**
 * @author tchambs
 * 
 */
public class GetFields_Result {

	private List<Field> fields;

	/**
	 * 
	 * @param fields
	 *            a list of each field
	 */
	public GetFields_Result(List<Field> fields) {
		setFields(fields);
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Field f : this.fields) {
			sb.append(f.getProjectID() + "\n");
			sb.append(f.getId() + "\n");
			sb.append(f.getTitle() + "\n");
		}
		
		return sb.toString();
	}
}
