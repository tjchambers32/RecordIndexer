/**
 * 
 */
package client.communication;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.ClientException;
import shared.*;
import shared.communication.*;
import shared.model.*;

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
	 * Test method for {@link client.communication.ClientCommunicator#ValidateUser(shared.communication.ValidateUser_Params)}.
	 * @throws ClientException 
	 */
	@Test
	public void testValidateUser() throws ClientException {
		//invalid user
		User user = new User("username", "password", "user", "name", "username@password.com", 100, -1);
		ValidateUser_Params params = new ValidateUser_Params(user);
		ValidateUser_Result result = comm.ValidateUser(params);
		
		assertEquals(null, result.getResult());
		
		//valid username and password
		user.setUsername("test1");
		user.setPassword("test1");
		
		params.setParams(user);
		result = comm.ValidateUser(params);
		
		assertEquals(result.getResult().getUsername(), "test1");
		assertEquals(result.getResult().getPassword(), "test1");
		
		assertEquals(result.getResult().getEmail(), "test1@gmail.com");
		
	}

	/**
	 * Test method for {@link client.communication.ClientCommunicator#getProjects(shared.communication.GetProjects_Params)}.
	 * @throws ClientException 
	 */
	@Test
	public void testGetProjects() throws ClientException {
		User user = new User("test1", "test1", "Test", "One", "test1@gmail.com", 0, -1);
		GetProjects_Params params = new GetProjects_Params(user);
		GetProjects_Result result = comm.getProjects(params);
		
		assertEquals(3, result.getProjects().size());
	}

	/**
	 * Test method for {@link client.communication.ClientCommunicator#getSampleImage(shared.communication.GetSampleImage_Params)}.
	 * @throws ClientException 
	 */
	@Test
	public void testGetSampleImage() throws ClientException {
		User user = new User("test1", "test1", "Test", "One", "test1@gmail.com", 0, -1);
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
	 * Test method for {@link client.communication.ClientCommunicator#downloadBatch(shared.communication.DownloadBatch_Params)}.
	 */
	@Test
	public void testDownloadBatch() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link client.communication.ClientCommunicator#submitBatch(shared.communication.SubmitBatch_Params)}.
	 */
	@Test
	public void testSubmitBatch() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link client.communication.ClientCommunicator#getFields(shared.communication.GetFields_Params)}.
	 */
	@Test
	public void testGetFields() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link client.communication.ClientCommunicator#search(shared.communication.Search_Params)}.
	 */
	@Test
	public void testSearch() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link client.communication.ClientCommunicator#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

}
