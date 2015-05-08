/**
 * 
 */
package test.server.database;

import static org.junit.Assert.*;

import org.junit.*;
import main.server.database.*;
import main.shared.model.*;
import java.util.*;
/**
 * @author tchambs
 *
 */
public class ValueDAOTest {

	private Database db;
	private ValueDAO daoDB;

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
		// Delete all Fields from database
		db = new Database();
		db.startTransaction();

		List<Value> values = db.getValueDAO().getAll();

		for (Value v : values) {
			db.getValueDAO().delete(v);
		}
		db.endTransaction(true);

		// Prepare database for test cases
		db = new Database();
		db.startTransaction();
		daoDB = db.getValueDAO();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		daoDB = null;
	}


	/**
	 * Test method for {@link server.database.ValueDAO#getAll()}.
	 * @throws DatabaseException 
	 */
	@Test
	public void testGetAll() throws DatabaseException {
		
		List<Value> all = daoDB.getAll();
		assertEquals(0, all.size());
		
		Value one = new Value(1, 1, "one", 1);
		Value two = new Value(2, 2, "two", 2);
		Value three = new Value(3, 3, "three", 3);
		
		daoDB.add(one);
		daoDB.add(two);
		daoDB.add(three);
		
		all = daoDB.getAll();
		assertEquals(3, all.size());
	}

	/**
	 * Test method for {@link server.database.ValueDAO#add(shared.model.Value)}.
	 * @throws DatabaseException 
	 */
	@Test
	public void testAdd() throws DatabaseException {
		Value one = new Value(1, 1, "one", 1);
		Value two = new Value(2, 2, "two", 2);
		Value three = new Value(3, 3, "three", 3);
		
		daoDB.add(one);
		daoDB.add(two);
		daoDB.add(three);
		
		List<Value> all = daoDB.getAll();
		assertEquals(3, all.size());
		
		boolean found1 = false;
		boolean found2 = false;
		boolean found3 = false;
		
		for (Value i : all) {
			if (!found1) {
				found1 = areEqual(i, one, false);
			}
			if (!found2) {
				found2 = areEqual(i, two, false);
			}
			if (!found3) {
				found3 = areEqual(i, three, false);
			}
		}
		assertTrue(found1 && found2 && found3);
	}

	/**
	 * Test method for {@link server.database.ValueDAO#update(shared.model.Value)}.
	 * @throws DatabaseException 
	 */
	@Test
	public void testUpdate() throws DatabaseException {
		Value one = new Value(1, 1, "one", 1);
		Value two = new Value(2, 2, "two", 2);
		Value three = new Value(3, 3, "three", 3);
		
		daoDB.add(one);
		daoDB.add(two);
		daoDB.add(three);
		
		List<Value> all = daoDB.getAll();
		assertEquals(3, all.size());
		
		one.setFieldNumber(100);
		one.setRecordID(100);
		one.setText("1");
		
		two.setFieldNumber(200);
		two.setRecordID(200);
		two.setText("2");
		
		daoDB.update(one);
		daoDB.update(two);
		daoDB.update(three);
		
		all = daoDB.getAll();
		assertEquals(3, all.size());
		
		boolean found1 = false;
		boolean found2 = false;
		boolean found3 = false;
		
		for (Value i : all) {
			if (!found1) {
				found1 = areEqual(i, one, false);
			}
			if (!found2) {
				found2 = areEqual(i, two, false);
			}
			if (!found3) {
				found3 = areEqual(i, three, false);
			}
		}
		assertTrue(found1 && found2 && found3);
	}

	/**
	 * Test method for {@link server.database.ValueDAO#delete(shared.model.Value)}.
	 * @throws DatabaseException 
	 */
	@Test
	public void testDelete() throws DatabaseException {
		Value one = new Value(1, 1, "one", 1);
		Value two = new Value(2, 2, "two", 2);
		Value three = new Value(3, 3, "three", 3);
		
		daoDB.add(one);
		daoDB.add(two);
		daoDB.add(three);
		
		List<Value> all = daoDB.getAll();
		assertEquals(3, all.size());
		
		daoDB.delete(one);
		
		all = daoDB.getAll();
		assertEquals(2, all.size());
		
		daoDB.delete(two);
		
		all = daoDB.getAll();
		assertEquals(1, all.size());
		
		daoDB.delete(three);
		
		all = daoDB.getAll();
		assertEquals(0, all.size());
	}

	private boolean areEqual(Value a, Value b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}

		return (safeEquals(a.getRecordID(), b.getRecordID())
				&& safeEquals(a.getText(), b.getText())
				&& safeEquals(a.getFieldNumber(), b.getFieldNumber()));
	}

	private boolean safeEquals(Object a, Object b) {
		if (a == null || b == null) {
			return (a == null && b == null);
		} else {
			return a.equals(b);
		}
	}
	
}
