/**
 *@author tchambs
 * 
 */

package client.communication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import shared.communication.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class ClientCommunicator {

	private String URL_PREFIX;
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";
	
	public ClientCommunicator() {
		setURL_PREFIX("http://localhost:8080");
	}

	public ClientCommunicator(String host, int port) {
		String prefix = "http://" + host + ":" + port;
		setURL_PREFIX(prefix);
	}
	
	public String getURL_PREFIX() {
		return URL_PREFIX;
	}


	public void setURL_PREFIX(String uRL_PREFIX) {
		URL_PREFIX = uRL_PREFIX;
	}


	/**
	* Validates the user.
	* <p>If successful, returns a string including these lines:</p>
	* TRUE <br>
	* User_First_Name <br>
	* User_Last_Name <br>
	* Num_Records <br>
	* <p>Otherwise, returns "FALSE" or "FAILED".</p>
	* @param user the username of the user
	* @param password the password of the user
	* @return  If successful, the multi-line string described above, 
	*    beginning with "TRUE". Returns "FALSE" if the user credentials 
	*    are invalid. Returns "FAILED" if the operation failed for any 
	*    reason (e.g., can't connect to the server, internal server error).
	*/
	public ValidateUser_Result ValidateUser(ValidateUser_Params params) {
		
		
		return null;
	}
	
	/**
	 * Gets a list of the available projects
	 * 
	 * <p> Example: </p>
	 * <ul style="list-style: none;"> 
	 * <li> ProjectName </li>
	 * <li> ProjectID</li>
	 * <li> ProjectName </li>
	 * <li> ProjectID</li>	 
	 * <li> ProjectName </li>
	 * <li> ProjectID</li>
	 * </ul>
	 * 
	 * @param user the username of the user
	 * @param password the password of the user
	 * @return If successful, information about all of the 
	 * 		available projects as shown above. Returns 
	 * 		"FALSE" if the user credentials are invalid. 
	 * 		Returns "FAILED" if the operation failed for any reason
	 * 		(e.g., can't connect to the server, internal server error).
	 */
	public GetProjects_Result getProjects(GetProjects_Params params) {
		
		return null;
	}
	
	/**
	 * @param user the username of the user
	 * @param password the password of the user
	 * @param projectID the project ID
	 * @return a URL for a sample image for the specified project
	 */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params) {
		return null;
	}
	
	/**
	 * Downloads a batch for the user to index
	 * 
	 * <p> Example: </p>
	 * <ul style="list-style: none;"> 
	 * <li>BATCH_ID</li>
	 * <li>PROJECT_ID</li>
	 * <li>IMAGE_URL</li>
	 * <li>FIRST_Y_COORD</li>
	 * <li>RECORD_HEIGHT</li>
	 * <li>NUM_RECORDS</li>
	 * <li>NUM_FIELDS</li>
	 * <li>FIELD</li>
	 * 
	 * @param user the username of the user
	 * @param password the password of the user
	 * @param projectID the project ID
	 * @return  If successful, the multi-line string described above,
	 *		Returns "FALSE" if the user credentials 
	 *    	are invalid. Returns "FAILED" if the operation failed for any 
	 *    	reason (e.g., can't connect to the server, internal server error).
	 * 
	 */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params) {
		
		return null;
	}
	
	/**
	 * Submits the indexed record field values for a batch to the Server
	 * <br><br>
 	 * Example field values: <p>"Jones, Fred, 13; Rogers, Susan, 42; , , ; , , ; 
	 * 		Van Fleet, Bill, 23"</p>
	 * 
	 * @param user the username of the user
	 * @param password the password of the user
	 * @param batchID the Batch ID
	 * @param fieldValues field values for the batch, values are ordered
	 * 			by doing a left-to-right, top-to-bottom traversal of the batch<br>
	 * 			image.<!---> The values within a record are delimited by 
	 * 			commas.<!---> Records are delimited by semicolons.<!---> 
	 * 			Empty fields are <br>represented with empty strings.
	 * 
	 * 
	 * @return  If the operation succeeds returns TRUE, if it fails
	 * 		for any reason (e.g., invalid batch ID, invalid user name
	 * 		or password, user doesn’t own the submitted batch, wrong 
	 * 		number of values, can’t connect to the server, internal 
	 * 		server error,etc.) returns FAILED
	 * 
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params) {
		
		return null;
	}
	
	/**
	 * 
	 * Returns information about all of the fields for the specified project
	 * If no project is specified, returns information about all of the fields 
	 * for all projects in the system
	 * <br><br>
	 * Example:
	 * <ul style="list-style: none;">
	 * <li>ProjectID</li>
	 * <li>FieldID</li>
	 * <li>FieldTitle</li>
	 * <li>ProjectID</li>
	 * <li>FieldID</li>
	 * <li>FieldTitle</li>
	 * 
	 * @param user the username of the user
	 * @param password the password of the user
	 * @param projectID the Project ID or an empty string
	 * 
	 * @return  If the operation succeeds returns the above specified string, 
	 * 		if it fails for any reason (e.g., invalid batch ID, invalid user 
	 * 		name or password, user doesn’t own the submitted batch, wrong 
	 * 		number of values, can’t connect to the server, internal 
	 * 		server error,etc.) returns FAILED
	 * 
	 */
	public GetFields_Result getFields(GetFields_Params params) {
		
		return null;
	}
	
	/**
	 * 
	 * Searches the indexed records for the specified strings <br><br>
	 * The user specifies one or more fields to be searched, 
	 * and one or more strings to search for. The fields to
	 * be searched are specified by “field ID”. (Note, field IDs 
	 * are unique across all fields in the system.) <br><br>
	 * The Server searches all indexed records containing the specified 
	 * fields for the specified strings, and returns a list of 
	 * all matches. In order to constitute a match, a value must 
	 * appear in one of the search fields, and be exactly equal 
	 * (ignoring case) to one of the search strings. <br> <br>
	 * For each match found, the Server returns a tuple of the following 
	 * form: (Batch ID, Image URL, Record Number, Field ID)
	 * 
	 * <br><br>
	 * Example output:
	 * <ul style="list-style:none">
	 * <li>BATCH_ID</li>
	 * <li>IMAGE_URL</li>
	 * <li>RECORD_NUM</li>
	 * <li>FIELD_ID</li>
	 * 
	 * @param user the username of the user
	 * @param password the password of the user
	 * @param fields Comma-separated list of fields to be searched
	 * @param searchValues Comma-separated list of strings to search for
	 * 
	 * @return  If the operation succeeds returns the above specified string, 
	 * 		if it fails for any reason (e.g., invalid batch ID, invalid user 
	 * 		name or password, user doesn’t own the submitted batch, wrong 
	 * 		number of values, can’t connect to the server, internal 
	 * 		server error,etc.) returns FAILED
	 * 
	 */
	public Search_Result search(Search_Params params) {
		
		return null;
	}
	
	public Object doPost(String command, Object postData) {
		
		XStream xmlStream = new XStream(new DomDriver());
		Object result = null;
		
		try {
			URL url = new URL(URL_PREFIX + command);
			HttpURLConnection connection = null;
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();
			xmlStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
			
			//TODO: read in xstream and check for success
			//result = xmlStream.fromXML.....
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/**
	 * @return format everything correctly for sending to the HTTP Server
	 */
	public String toString() {
		
		
		return null;
	}
	
}
