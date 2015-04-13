/**
 * 
 */
package client.gui;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.DataImporter;
import shared.communication.*;
import shared.model.User;
import client.gui.batchstate.BatchState;
import client.gui.batchstate.Cell;

/**
 * @author tchambs
 * 
 */
public class QualityCheckerTest {

	BatchState batchState;
	static DataImporter importer;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		importer = new DataImporter();
		importer.doImport("Records/Records.xml");

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		importer = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		batchState = new BatchState("localhost", 39640);

		User user = new User("sheila", "parker");
		batchState.setUser(user);
		batchState.stateChanged();

		DownloadBatch_Params params = new DownloadBatch_Params(user, 1);

		DownloadBatch_Result result = batchState.getComm()
				.downloadBatch(params);

		batchState.setDownloadedBatch(result);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		batchState = null;
	}

	@Test
	public void test() {

		// last names
		batchState.setValue(new Cell(1, 1), "kidd");
		boolean trueTest = batchState.qualityCheck(new Cell(1, 1));
		assertTrue(trueTest);

		batchState.setValue(new Cell(1, 1), "kidde");
		boolean falseTest = batchState.qualityCheck(new Cell(1, 1));
		assertFalse(falseTest);

		Set<String> suggestions = batchState.makeSuggestions(new Cell(1, 1));
		assertTrue(suggestions.contains("kidd")); // edit distance 1
		assertTrue(suggestions.contains("riddle")); // edit distance 2
		assertEquals(2, suggestions.size());
		
		
	}

}
