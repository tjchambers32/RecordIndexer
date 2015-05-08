/**
 * 
 */
package test.client.communication;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.client.ClientException;
import main.shared.*;
import main.shared.communication.*;
import main.shared.model.*;
import main.client.communication.*;
/**
 * @author tchambs
 * 
 */
public class ClientCommunicatorTest {

	ClientCommunicator comm;
	static DataImporter importer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		importer = new DataImporter();
		importer.doImport("Records/Records.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		importer = null;
	}

	@Before
	public void setUp() {
		comm = new ClientCommunicator("localhost", 39640);
	}

	@After
	public void tearDown() {
		comm = null;
	}

	/**
	 * Test method for
	 * {@link client.communication.ClientCommunicator#ValidateUser(shared.communication.ValidateUser_Params)}
	 * .
	 * 
	 * @throws ClientException
	 */
	@Test
	public void testValidateUser() throws ClientException {
		// invalid user
		User user = new User("username", "password", "user", "name",
				"username@password.com", 100, -1);
		ValidateUser_Params params = new ValidateUser_Params(user);
		ValidateUser_Result result = comm.ValidateUser(params);

		assertEquals(null, result.getResult());

		// valid username and password
		user.setUsername("test1");
		user.setPassword("test1");

		params.setParams(user);
		result = comm.ValidateUser(params);

		assertEquals(result.getResult().getUsername(), "test1");
		assertEquals(result.getResult().getPassword(), "test1");

		assertEquals(result.getResult().getEmail(), "test1@gmail.com");

	}

	/**
	 * Test method for
	 * {@link client.communication.ClientCommunicator#getProjects(shared.communication.GetProjects_Params)}
	 * .
	 * 
	 * @throws ClientException
	 */
	@Test
	public void testGetProjects() throws ClientException {
		User user = new User("test1", "test1", "Test", "One",
				"test1@gmail.com", 0, -1);
		GetProjects_Params params = new GetProjects_Params(user);
		GetProjects_Result result = comm.getProjects(params);

		assertEquals(3, result.getProjects().size());
	}

	/**
	 * Test method for
	 * {@link client.communication.ClientCommunicator#getSampleImage(shared.communication.GetSampleImage_Params)}
	 * .
	 * 
	 * @throws ClientException
	 */
	@Test
	public void testGetSampleImage() throws ClientException {
		User user = new User("test1", "test1", "Test", "One",
				"test1@gmail.com", 0, -1);
		GetSampleImage_Params params = new GetSampleImage_Params(user, 1);
		GetSampleImage_Result result = comm.getSampleImage(params);

		assertEquals(result.getImageURL(), "images/1890_image19.png");

		params = new GetSampleImage_Params(user, 2);
		result = comm.getSampleImage(params);

		assertEquals(result.getImageURL(), "images/1900_image19.png");

		params = new GetSampleImage_Params(user, 3);
		result = comm.getSampleImage(params);

		assertEquals(result.getImageURL(), "images/draft_image19.png");
	}

	/**
	 * Test method for
	 * {@link client.communication.ClientCommunicator#downloadBatch(shared.communication.DownloadBatch_Params)}
	 * .
	 * 
	 * @throws ClientException
	 */
	@Test
	public void testDownloadBatch() throws ClientException {
		User user = new User("test1", "test1", "Test", "One",
				"test1@gmail.com", 0, -1);
		DownloadBatch_Params params = new DownloadBatch_Params(user, 1);
		DownloadBatch_Result result = comm.downloadBatch(params);

		assertEquals(result.getProject().getId(), 1);
		assertEquals(result.getProject().getRecordsPerImage(), 8);
		assertEquals(result.getFields().get(0).getTitle(), "Last Name");
	}

	/**
	 * Test method for
	 * {@link client.communication.ClientCommunicator#submitBatch(shared.communication.SubmitBatch_Params)}
	 * .
	 * 
	 * @throws ClientException
	 */
	@Test
	public void testSubmitBatch() throws ClientException {
		User user = new User("test1", "test1", "Test", "One",
				"test1@gmail.com", 0, -1);
		DownloadBatch_Params dparams = new DownloadBatch_Params(user, 1);
		DownloadBatch_Result dresult = comm.downloadBatch(dparams);
		user.setImageID(dresult.getImage().getId());

		assertEquals(dresult.getProject().getId(), 1);
		assertEquals(dresult.getProject().getRecordsPerImage(), 8);
		assertEquals(dresult.getFields().get(0).getTitle(), "Last Name");

		String paramValues = "fox,russell,19,alaska native;bartlett,dave,21,hispanic;acosta,jerome,28,alaska native;";

		ArrayList<Record> records = new ArrayList<Record>();
		ArrayList<Value> valueList = new ArrayList<Value>();

		String[] recordString = paramValues.split("\\s*(;)\\s*");
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
		SubmitBatch_Result result = comm.submitBatch(params);

		assertEquals(result.getResult(), true);
	}

	/**
	 * Test method for
	 * {@link client.communication.ClientCommunicator#getFields(shared.communication.GetFields_Params)}
	 * .
	 * 
	 * @throws ClientException
	 */
	@Test
	public void testGetFields() throws ClientException {
		User user = new User("test1", "test1", "Test", "One",
				"test1@gmail.com", 0, -1);
		GetFields_Params params = new GetFields_Params(user, -1);
		GetFields_Result result = comm.getFields(params);

		assertEquals(result.getFields().size(), 13);

	}

	/**
	 * Test method for
	 * {@link client.communication.ClientCommunicator#search(shared.communication.Search_Params)}
	 * .
	 * 
	 * @throws ClientException
	 */
	@Test
	public void testSearch() throws ClientException {
		User user = new User("test1", "test1", "Test", "One",
				"test1@gmail.com", 0, -1);

		String fieldInput = "1,2,3";
		String valueInput = "fox,russell, 21, 1, 2, 3";

		ArrayList<Integer> fields = new ArrayList<Integer>();
		ArrayList<String> searchValues = new ArrayList<String>();

		String[] fieldString = fieldInput.split("\\s*(,)\\s*");
		String[] valueString = valueInput.split("\\s*(,)\\s*");

		for (String s : fieldString) {
			fields.add(Integer.parseInt(s));
		}
		for (String s : valueString) {
			if (!s.equals(""))
				searchValues.add(s.toLowerCase());
		}

		Search_Params params = new Search_Params(user, fields, searchValues);
		ArrayList<Search_Result> result = comm.search(params);

		assertNotNull(result.get(0));

		for (int i = 0; i < result.size(); i++) {
			if (result.get(i).getFieldID() != 1
					&& result.get(i).getFieldID() != 2
					&& result.get(i).getFieldID() != 3)
				fail("Search results had FieldID not listed in search parameters");
		}

	}
}
