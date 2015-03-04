/**
 * 
 */
package server.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.*;
import shared.model.*;

import java.util.*;
/**
 * @author tchambs
 *
 */
public class RecordDAOTest {

	private Database db;
	private RecordDAO dbRecords;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database.initialize();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		db = new Database();
		db.startTransaction();
		
		List<Record> records = db.getRecordDAO().getAll();
		
		for (Record r : records) {
			db.getRecordDAO().delete(r);
		}
		db.endTransaction(true);
		
		//Prepare database for test cases
		db = new Database();
		db.startTransaction();
		dbRecords = db.getRecordDAO();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		dbRecords = null;
	}

	/**
	 * Test method for {@link server.database.RecordDAO#add(shared.model.Record)}.
	 * @throws DatabaseException 
	 */
	@Test
	public void testAdd() throws DatabaseException {
		Record ten = new Record(10, 10, 10);
		Record twenty = new Record(20, 20, 20);
		Record thirty = new Record(30, 30, 30);
		
		List<Record> allRecords = dbRecords.getAll();
		
		assertEquals(0, allRecords.size());
		
		dbRecords.add(ten);
		dbRecords.add(twenty);
		dbRecords.add(thirty);
		
		allRecords = dbRecords.getAll();
		assertEquals(3, allRecords.size());
	}

	/**
	 * Test method for {@link server.database.RecordDAO#update(shared.model.Record)}.
	 */
	@Test
	public void testUpdate() throws DatabaseException {
		Record ten = new Record(10, 10, 10);
		Record twenty = new Record(20, 20, 20);
		Record thirty = new Record(30, 30, 30);

		dbRecords.add(ten);
		dbRecords.add(twenty);
		dbRecords.add(thirty);

		ten.setImageID(100);
		ten.setRowNumber(100);
		
		twenty.setImageID(200);
		twenty.setRowNumber(200);
		
		thirty.setImageID(300);
		thirty.setRowNumber(300);
		
		dbRecords.update(ten);
		dbRecords.update(twenty);
		dbRecords.update(thirty);

		
		boolean foundTen = false;
		boolean foundTwenty = false;
		boolean foundThirty = false;
		List<Record> all = dbRecords.getAll();
		for (Record p : all) {
			
			if (!foundTen) {
				foundTen = areEqual(p, ten, false);
			}		
			if (!foundTwenty) {
				foundTwenty = areEqual(p, twenty, false);
			}
			if (!foundThirty) {
				foundThirty = areEqual(p, twenty, false);
			}
		}
		
		assertTrue(foundTen && foundTwenty && foundThirty);
	}

	/**
	 * Test method for {@link server.database.RecordDAO#delete(shared.model.Record)}.
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented"); // TODO
	}
	private boolean areEqual(Record a, Record b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}

		//TODO: FIX THESE
		return (safeEquals(a.getTitle(), b.getTitle())
				&& safeEquals(a.getRecordsPerImage(), b.getRecordsPerImage())
				&& safeEquals(a.getFirstYCoord(), b.getFirstYCoord())
				&& safeEquals(a.getRecordHeight(), b.getRecordHeight()));

	}

	private boolean safeEquals(Object a, Object b) {
		if (a == null || b == null) {
			return (a == null && b == null);
		} else {
			return a.equals(b);
		}
	}
}
