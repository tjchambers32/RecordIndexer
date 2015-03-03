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

/**
 * @author tchambs
 * 
 */
public class UserDAOTest {
	private Database db;
	private UserDAO dbUsers;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}

	@Before
	public void setUp() throws Exception {
		// Delete all Users from database
		db = new Database();
		db.startTransaction();

		List<User> users = db.getUserDAO().getAll();

		for (User u : users) {
			db.getUserDAO().delete(u);
		}

		db.endTransaction(true);

		// Prep for next test case
		db = new Database();
		db.startTransaction();
		dbUsers = db.getUserDAO();
	}

	@After
	public void tearDown() throws Exception {
		// Roll back to undo changes

		db.endTransaction(false);
		db = null;
		dbUsers = null;
	}

	@Test
	public void testAdd() throws DatabaseException {
		User bob = new User(-1, "bobUser", "Bob", "Smith", "bob@company.com",
				"topsecret", 20, 8);
		User jack = new User(-1, "jackie", "Jack", "Cooper",
				"jack@company.com", "iLoveCheese", 43, 5);
		User sue = new User(-1, "sueornottosue", "Sue", "Rodriguez",
				"sue@company.com", "litigation", 3, 7);

		dbUsers.add(bob);
		dbUsers.add(jack);
		dbUsers.add(sue);

		List<User> allUsers = dbUsers.getAll();
		assertEquals(3, allUsers.size());

		boolean foundBob = false;
		boolean foundJack = false;
		boolean foundSue = false;

		for (User u : allUsers) {
			if (!foundBob) {
				foundBob = areEqual(u, bob, false);
			}

			if (!foundSue) {
				foundSue = areEqual(u, sue, false);
			}

			if (!foundJack) {
				foundJack = areEqual(u, jack, false);
			}
		}

		assertTrue(foundBob && foundSue && foundJack);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
	}

	@Test
	public void testDelete() throws DatabaseException {

	}

	@Test(expected = DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		User invalid = new User(-1, "", " ", "asdf", "", null, 0, 0);
		dbUsers.add(invalid);
	}

	private boolean areEqual(User a, User b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}

		return (safeEquals(a.getFirstName(), b.getFirstName())
				&& safeEquals(a.getPassword(), b.getPassword())
				&& safeEquals(a.getUsername(), b.getUsername())
				&& safeEquals(a.getEmail(), b.getEmail()) && a
					.getRecordsIndexed() == b.getRecordsIndexed());

	}

	private boolean safeEquals(Object a, Object b) {
		if (a == null || b == null) {
			return (a == null && b == null);
		} else {
			return a.equals(b);
		}
	}
}
