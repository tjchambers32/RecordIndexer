/**
 * 
 */
package test.java.server.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.server.database.*;
import main.java.shared.model.*;

import java.util.*;
/**
 * @author tchambs
 *
 */
public class FieldDAOTest {

	private Database db;
	private FieldDAO daoDB;

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

		List<Field> fields = db.getFieldDAO().getAll();

		for (Field f : fields) {
			db.getFieldDAO().delete(f);
		}
		db.endTransaction(true);

		// Prepare database for test cases
		db = new Database();
		db.startTransaction();
		daoDB = db.getFieldDAO();
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
	 * Test method for {@link server.database.FieldDAO#add(shared.model.Field)}.
	 * @throws DatabaseException 
	 */
	@Test
	public void testAdd() throws DatabaseException {
		Field one = new Field(1, "one", 1, 1, "one", "one", 1);
		Field two = new Field(2, "two", 2, 2, "two", "two", 2);
		Field three = new Field(3, "three", 3, 3, "three", "three", 3);
		
		daoDB.add(one);
		daoDB.add(two);
		daoDB.add(three);
		
		List<Field> all = daoDB.getAll();
		assertEquals(3, all.size());
		
		boolean found1 = false;
		boolean found2 = false;
		boolean found3 = false;
		
		for (Field i : all) {
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
	 * Test method for {@link server.database.FieldDAO#update(shared.model.Field)}.
	 * @throws DatabaseException 
	 */
	@Test
	public void testUpdate() throws DatabaseException {
		Field one = new Field(1, "one", 1, 1, "one", "one", 1);
		Field two = new Field(2, "two", 2, 2, "two", "two", 2);
		Field three = new Field(3, "three", 3, 3, "three", "three", 3);
		
		daoDB.add(one);
		daoDB.add(two);
		daoDB.add(three);
		
		List<Field> all = daoDB.getAll();
		assertEquals(3, all.size());
		
		one.setTitle("1");
		one.setProjectID(100);
		one.setWidth(100);
		one.setxCoord(100);
		one.setHelpHTML("1");
		one.setKnownData("1");

		two.setTitle("2");
		two.setProjectID(200);
		two.setWidth(200);
		two.setxCoord(200);
		two.setHelpHTML("2");
		two.setKnownData("2");
		
		three.setTitle("3");
		three.setProjectID(300);
		three.setWidth(300);
		three.setxCoord(300);
		three.setHelpHTML("3");
		three.setKnownData("3");
		
		daoDB.update(one);
		daoDB.update(two);
		daoDB.update(three);
		
		all = daoDB.getAll();
		assertEquals(3, all.size());
		
		boolean found1 = false;
		boolean found2 = false;
		boolean found3 = false;
		
		for (Field i : all) {
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
	 * Test method for {@link server.database.FieldDAO#delete(shared.model.Field)}.
	 * @throws DatabaseException 
	 */
	@Test
	public void testDelete() throws DatabaseException {
		Field one = new Field(1, "one", 1, 1, "one", "one", 1);
		Field two = new Field(2, "two", 2, 2, "two", "two", 2);
		Field three = new Field(3, "three", 3, 3, "three", "three", 3);
		
		daoDB.add(one);
		daoDB.add(two);
		daoDB.add(three);
		
		List<Field> all = daoDB.getAll();
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
	
	private boolean areEqual(Field a, Field b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}

		return (safeEquals(a.getTitle(), b.getTitle())
				&& safeEquals(a.getxCoord(), b.getxCoord())
				&& safeEquals(a.getWidth(), b.getWidth())
				&& safeEquals(a.getHelpHTML(), b.getHelpHTML())
				&& safeEquals(a.getKnownData(), b.getKnownData())
				&& safeEquals(a.getProjectID(), b.getProjectID()));
	}

	private boolean safeEquals(Object a, Object b) {
		if (a == null || b == null) {
			return (a == null && b == null);
		} else {
			return a.equals(b);
		}
	}
}
