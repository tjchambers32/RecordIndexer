
package server.database;


import static org.junit.Assert.*;
import shared.model.*;
import java.util.*;
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
		User bob = new User("bob", "Bob", "White", "bob@white.org",
				"http://www.white.org/bob", 20, 8);
		User kels = new User("kchambers", "kelsey", "chambers",
				"kelsey@chambers.com", "kels", 43, 5);
		User rock = new User("rocky", "Rockwell", "Chambers",
				"rocky@chambers.com", "rock", 3, 7);

		dbUsers.add(bob);
		dbUsers.add(kels);
		dbUsers.add(rock);

		List<User> allUsers = dbUsers.getAll();
		assertEquals(3, allUsers.size());

		boolean foundBob = false;
		boolean foundKels = false;
		boolean foundRock = false;

		for (User u : allUsers) {
			if (!foundBob) {
				foundBob = areEqual(u, bob, false);
			}

			if (!foundKels) {
				foundKels = areEqual(u, kels, false);
			}

			if (!foundRock) {
				foundRock = areEqual(u, rock, false);
			}
		}

		assertTrue(foundBob && foundKels && foundRock);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		User bob = new User("bob", "Bob", "White", "bob@white.org",
				"http://www.white.org/bob", 20, 8);
		User kels = new User("kchambers", "kelsey", "chambers",
				"kelsey@chambers.com", "kels", 43, 5);
		User rock = new User("rocky", "Rockwell", "Chambers",
				"rocky@chambers.com", "rock", 3, 7);

		dbUsers.add(bob);
		dbUsers.add(kels);
		dbUsers.add(rock);

		List<User> allUsers = dbUsers.getAll();
		assertEquals(3, allUsers.size());
		
		bob.setEmail("bob@chambers");
		bob.setFirstName("travis");
		bob.setLastName("Chambers");
		bob.setImageID(100);
		bob.setPassword("letMeIN!");
		bob.setRecordsIndexed(100000);

		dbUsers.update(bob);
		
		allUsers = dbUsers.getAll();
		assertEquals(3, allUsers.size());
		
		boolean foundBob = false;
		boolean foundKels = false;
		boolean foundRock = false;
		
		for (User u : allUsers) {
			if (!foundBob) {
				foundBob = areEqual(u, bob, false);
			}

			if (!foundKels) {
				foundKels = areEqual(u, kels, false);
			}

			if (!foundRock) {
				foundRock = areEqual(u, rock, false);
			}
		}

		assertTrue(foundBob && foundKels && foundRock);

	}

	@Test
	public void testDelete() throws DatabaseException {
		User bob = new User("bob", "Bob", "White", "bob@white.org",
				"http://www.white.org/bob", 20, 8);
		User kels = new User("kchambers", "kelsey", "chambers",
				"kelsey@chambers.com", "kels", 43, 5);
		User rock = new User("rocky", "Rockwell", "Chambers",
				"rocky@chambers.com", "rock", 3, 7);

		dbUsers.add(bob);
		dbUsers.add(kels);
		dbUsers.add(rock);

		List<User> allUsers = dbUsers.getAll();
		assertEquals(3, allUsers.size());
		
		dbUsers.delete(bob);
		dbUsers.delete(kels);
		dbUsers.delete(rock);
		
		allUsers = dbUsers.getAll();
		assertEquals(0, allUsers.size());
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
