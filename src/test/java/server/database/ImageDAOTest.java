/**
 * 
 */
package test.java.server.database;

import static org.junit.Assert.*;

import org.junit.*;

import main.java.server.database.*;
import main.java.shared.model.*;

import java.util.*;

/**
 * @author tchambs
 * 
 */
public class ImageDAOTest {

	private Database db;
	private ImageDAO daoDB;

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
		// Delete all Projects from database
		db = new Database();
		db.startTransaction();

		List<Image> images = db.getImageDAO().getAll();

		for (Image i : images) {
			db.getImageDAO().delete(i);
		}
		db.endTransaction(true);

		// Prepare database for test cases
		db = new Database();
		db.startTransaction();
		daoDB = db.getImageDAO();
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
	 * Test method for {@link server.database.ImageDAO#add(shared.model.Image)}.
	 * @throws DatabaseException 
	 */
	@Test
	public void testAdd() throws DatabaseException {
		Image one = new Image(1, 100, "filepath", -1);
		Image two = new Image(2, 200, "filepath", 0);
		Image three = new Image(3, 300, "filepath", 1);
		
		daoDB.add(one);
		daoDB.add(two);
		daoDB.add(three);
		
		List<Image> all = daoDB.getAll();
		assertEquals(3, all.size());
		
		boolean found1 = false;
		boolean found2 = false;
		boolean found3 = false;
		
		for (Image i : all) {
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
	 * Test method for
	 * {@link server.database.ImageDAO#update(shared.model.Image)}.
	 * @throws DatabaseException 
	 */
	@Test
	public void testUpdate() throws DatabaseException {
		Image one = new Image(1, 100, "filepath", -1);
		Image two = new Image(2, 200, "filepath", 0);
		Image three = new Image(3, 300, "filepath", 1);
		
		daoDB.add(one);
		daoDB.add(two);
		daoDB.add(three);
		
		List<Image> all = daoDB.getAll();
		assertEquals(3, all.size());
		
		one.setProjectID(111);
		two.setProjectID(222);
		three.setProjectID(333);
		
		one.setFilepath("test");
		two.setFilepath("test");
		three.setFilepath("test");
		
		one.setAvailability(1);
		two.setAvailability(-1);
		three.setAvailability(0);
		
		daoDB.update(one);
		daoDB.update(two);
		daoDB.update(three);
		
		all = daoDB.getAll();
		assertEquals(3, all.size());
		
		boolean found1 = false;
		boolean found2 = false;
		boolean found3 = false;
		
		for (Image i : all) {
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
	 * Test method for
	 * {@link server.database.ImageDAO#delete(shared.model.Image)}.
	 * @throws DatabaseException 
	 */
	@Test
	public void testDelete() throws DatabaseException {
		Image one = new Image(1, 100, "filepath", -1);
		Image two = new Image(2, 200, "filepath", 0);
		Image three = new Image(3, 300, "filepath", 1);
		
		daoDB.add(one);
		daoDB.add(two);
		daoDB.add(three);
		
		List<Image> all = daoDB.getAll();
		assertEquals(3, all.size());
		
		daoDB.delete(one);
		daoDB.delete(two);
		daoDB.delete(three);
		
		all = daoDB.getAll();
		
		assertEquals(0, all.size());
	}
	private boolean areEqual(Image a, Image b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}

		return (safeEquals(a.getProjectID(), b.getProjectID())
				&& safeEquals(a.getFilepath(), b.getFilepath())
				&& safeEquals(a.getAvailability(), b.getAvailability()));
	}

	private boolean safeEquals(Object a, Object b) {
		if (a == null || b == null) {
			return (a == null && b == null);
		} else {
			return a.equals(b);
		}
	}
}
