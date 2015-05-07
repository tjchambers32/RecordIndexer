/**
 *@author tchambs
 * 
 */

package client.communication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import shared.communication.*;
import client.ClientException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ClientCommunicator {

	private String URL_PREFIX;
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";

	private XStream xmlStream;

	public ClientCommunicator() {
		setURL_PREFIX("http://localhost:39640");
		xmlStream = new XStream(new DomDriver());
	}

	public ClientCommunicator(String host, int port) {
		String prefix = "http://" + host + ":" + port;
		setURL_PREFIX(prefix);
		xmlStream = new XStream(new DomDriver());
	}

	public String getURL_PREFIX() {
		return URL_PREFIX;
	}

	public void setURL_PREFIX(String uRL_PREFIX) {
		URL_PREFIX = uRL_PREFIX;
	}

	/**
	 * Validates the user.
	 * <p>
	 * If successful, returns a string including these lines:
	 * </p>
	 * TRUE <br>
	 * User_First_Name <br>
	 * User_Last_Name <br>
	 * Num_Records <br>
	 * <p>
	 * Otherwise, returns "FALSE" or "FAILED".
	 * </p>
	 * 
	 * @param user
	 *            the username of the user
	 * @param password
	 *            the password of the user
	 * @return If successful, the multi-line string described above, beginning
	 *         with "TRUE". Returns "FALSE" if the user credentials are invalid.
	 *         Returns "FAILED" if the operation failed for any reason (e.g.,
	 *         can't connect to the server, internal server error).
	 * @throws ClientException
	 */
	public ValidateUser_Result ValidateUser(ValidateUser_Params params)
			throws ClientException {
		
		return (ValidateUser_Result) doPost("/ValidateUser", params);
	}

	/**
	 * Gets a list of the available projects
	 * 
	 * <p>
	 * Example:
	 * </p>
	 * <ul style="list-style: none;">
	 * <li>ProjectName</li>
	 * <li>ProjectID</li>
	 * <li>ProjectName</li>
	 * <li>ProjectID</li>
	 * <li>ProjectName</li>
	 * <li>ProjectID</li>
	 * </ul>
	 * 
	 * @param user
	 *            the username of the user
	 * @param password
	 *            the password of the user
	 * @return If successful, information about all of the available projects as
	 *         shown above. Returns "FALSE" if the user credentials are invalid.
	 *         Returns "FAILED" if the operation failed for any reason (e.g.,
	 *         can't connect to the server, internal server error).
	 * @throws ClientException
	 */
	public GetProjects_Result getProjects(GetProjects_Params params)
			throws ClientException {

		return (GetProjects_Result) doPost("/GetProjects", params);
	}

	/**
	 * @param user
	 *            the username of the user
	 * @param password
	 *            the password of the user
	 * @param projectID
	 *            the project ID
	 * @return a URL for a sample image for the specified project
	 * @throws ClientException 
	 */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params) throws ClientException {
		
		return (GetSampleImage_Result) doPost("/GetSampleImage", params);
	}

	public void downloadFile(String url) throws ClientException {
		
		doGet("/" + url);
	}
	
	/**
	 * Downloads a batch for the user to index
	 * 
	 * <p>
	 * Example:
	 * </p>
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
	 * @param user
	 *            the username of the user
	 * @param password
	 *            the password of the user
	 * @param projectID
	 *            the project ID
	 * @return If successful, the multi-line string described above, Returns
	 *         "FALSE" if the user credentials are invalid. Returns "FAILED" if
	 *         the operation failed for any reason (e.g., can't connect to the
	 *         server, internal server error).
	 * @throws ClientException 
	 * 
	 */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params) throws ClientException {

		return (DownloadBatch_Result) doPost("/DownloadBatch", params);
	}

	/**
	 * Submits the indexed record field values for a batch to the Server <br>
	 * <br>
	 * Example field values:
	 * <p>
	 * "Jones, Fred, 13; Rogers, Susan, 42; , , ; , , ; Van Fleet, Bill, 23"
	 * </p>
	 * 
	 * @param user
	 *            the username of the user
	 * @param password
	 *            the password of the user
	 * @param batchID
	 *            the Batch ID
	 * @param fieldValues
	 *            field values for the batch, values are ordered by doing a
	 *            left-to-right, top-to-bottom traversal of the batch<br>
	 *            image.<!---> The values within a record are delimited by
	 *            commas.<!---> Records are delimited by semicolons.<!---> Empty
	 *            fields are <br>
	 *            represented with empty strings.
	 * 
	 * 
	 * @return If the operation succeeds returns TRUE, if it fails for any
	 *         reason (e.g., invalid batch ID, invalid user name or password,
	 *         user doesnt own the submitted batch, wrong number of values,
	 *         can’t connect to the server, internal server error,etc.) returns
	 *         FAILED
	 * @throws ClientException 
	 * 
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params) throws ClientException {

		return (SubmitBatch_Result) doPost("/SubmitBatch", params);
	}

	/**
	 * 
	 * Returns information about all of the fields for the specified project If
	 * no project is specified, returns information about all of the fields for
	 * all projects in the system <br>
	 * <br>
	 * Example:
	 * <ul style="list-style: none;">
	 * <li>ProjectID</li>
	 * <li>FieldID</li>
	 * <li>FieldTitle</li>
	 * <li>ProjectID</li>
	 * <li>FieldID</li>
	 * <li>FieldTitle</li>
	 * 
	 * @param user
	 *            the username of the user
	 * @param password
	 *            the password of the user
	 * @param projectID
	 *            the Project ID or an empty string
	 * 
	 * @return If the operation succeeds returns the above specified string, if
	 *         it fails for any reason (e.g., invalid batch ID, invalid user
	 *         name or password, user doesn’t own the submitted batch, wrong
	 *         number of values, can’t connect to the server, internal server
	 *         error,etc.) returns FAILED
	 * @throws ClientException 
	 * 
	 */
	public GetFields_Result getFields(GetFields_Params params) throws ClientException {

		return (GetFields_Result) doPost("/GetFields", params);
	}

	/**
	 * 
	 * Searches the indexed records for the specified strings <br>
	 * <br>
	 * The user specifies one or more fields to be searched, and one or more
	 * strings to search for. The fields to be searched are specified by field
	 * ID. (Note, field IDs are unique across all fields in the system.) <br>
	 * <br>
	 * The Server searches all indexed records containing the specified fields
	 * for the specified strings, and returns a list of all matches. In order to
	 * constitute a match, a value must appear in one of the search fields, and
	 * be exactly equal (ignoring case) to one of the search strings. <br>
	 * <br>
	 * For each match found, the Server returns a tuple of the following form:
	 * (Batch ID, Image URL, Record Number, Field ID)
	 * 
	 * <br>
	 * <br>
	 * Example output:
	 * <ul style="list-style:none">
	 * <li>BATCH_ID</li>
	 * <li>IMAGE_URL</li>
	 * <li>RECORD_NUM</li>
	 * <li>FIELD_ID</li>
	 * 
	 * @param user
	 *            the username of the user
	 * @param password
	 *            the password of the user
	 * @param fields
	 *            Comma-separated list of fields to be searched
	 * @param searchValues
	 *            Comma-separated list of strings to search for
	 * 
	 * @return If the operation succeeds returns the above specified string, if
	 *         it fails for any reason (e.g., invalid batch ID, invalid user
	 *         name or password, user doesn’t own the submitted batch, wrong
	 *         number of values, can’t connect to the server, internal server
	 *         error,etc.) returns FAILED
	 * @throws ClientException 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Search_Result> search(Search_Params params) throws ClientException {

		return (ArrayList<Search_Result>) doPost("/Search", params);
	}

	/**
	 * 
	 * @param urlPath
	 * @return
	 * @throws ClientException
	 */
	private Object doGet(String urlPath) throws ClientException {
		try {
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod(HTTP_GET);
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Object result = xmlStream.fromXML(connection.getInputStream());
				return result;
			} else {
				throw new ClientException(String.format(
						"doGet failed: %s (http code %d)", urlPath,
						connection.getResponseCode()));
			}
		} catch (IOException e) {
			throw new ClientException(String.format("doGet failed: %s",
					e.getMessage()), e);
		}
	}

	/**
	 * 
	 * @param urlPath
	 * @param postData
	 * @return
	 * @throws ClientException
	 */
	private Object doPost(String urlPath, Object postData)
			throws ClientException {

		try {
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();

			xmlStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
			int test = connection.getResponseCode();
	
			if (test == HttpURLConnection.HTTP_OK) {
				Object result = xmlStream.fromXML(connection.getInputStream());
				return result;
			} else {
				throw new ClientException(String.format(
						"doPost failed: %s (http code %d)", urlPath,
						connection.getResponseCode()));
			}
		} catch (IOException e) {
			throw new ClientException(String.format("doPost failed: %s",
					e.getMessage()), e);
		}
	}

	/**
	 * @return format everything correctly for sending to the HTTP Server
	 */
	public String toString() {

		return null;
	}

}
