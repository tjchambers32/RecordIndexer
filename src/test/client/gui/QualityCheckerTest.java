/**
 * 
 */
package test.client.gui;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.shared.DataImporter;
import main.shared.communication.*;
import main.shared.model.User;
import main.client.gui.batchstate.BatchState;
import main.client.gui.batchstate.Cell;

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

		// first names
		batchState.setValue(new Cell(1, 2), "abe");
		trueTest = batchState.qualityCheck(new Cell(1, 2));
		assertTrue(trueTest);

		batchState.setValue(new Cell(1, 2), "aba");
		falseTest = batchState.qualityCheck(new Cell(1, 2));
		assertFalse(falseTest);

		suggestions = batchState.makeSuggestions(new Cell(1, 2));
		assertTrue(suggestions.contains("abe")); // edit distance 1
		assertTrue(suggestions.contains("abel")); // edit distance 2
		assertEquals(16, suggestions.size());

		// gender
		batchState.setValue(new Cell(1, 3), "f");
		trueTest = batchState.qualityCheck(new Cell(1, 3));
		assertTrue(trueTest);

		batchState.setValue(new Cell(1, 3), "fe");
		falseTest = batchState.qualityCheck(new Cell(1, 3));
		assertFalse(falseTest);

		suggestions = batchState.makeSuggestions(new Cell(1, 3));
		assertTrue(suggestions.contains("f")); // edit distance 1
		assertTrue(suggestions.contains("m")); // edit distance 2
		assertEquals(2, suggestions.size());

		// age
		for (int i = 0; i < 100; i++) {
			batchState.setValue(new Cell(1, 4), Integer.toString(i));
			trueTest = batchState.qualityCheck(new Cell(1, 4));
			assertTrue(trueTest); // quality check should ALWAYS return true for
									// any age
		}
	}

}
