package server.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.*;

import java.util.*;

/**
 * 
 */

/**
 * @author tchambs
 *
 */
public class ProjectDAOTest {

	private Database db;
	private ProjectDAO dbProjects;
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
		//Delete all Projects from database
		db = new Database();
		db.startTransaction();
		
		List<Project> projects = db.getProjectDAO().getAll();
		
		for (Project project : projects) {
			db.getProjectDAO().delete(project);
		}
		db.endTransaction(true);
		
		// Prepare database for test cases
		db = new Database();
		db.startTransaction();
		dbProjects = db.getProjectDAO();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		dbProjects = null;
	}

	/**
	 * Test method for {@link server.database.ProjectDAO#add(shared.model.Project)}.
	 */
	@Test
	public void testAdd() throws DatabaseException {
		Project spain = new Project("spain", 3, 10, 5);
		Project america = new Project("america", 1, 1, 1);
		Project canada = new Project("canada", 5, 10, 40);
		
		dbProjects.add(spain);
		dbProjects.add(america);
		dbProjects.add(canada);
		
		List<Project> allProjects = dbProjects.getAll();
		assertEquals(3, allProjects.size());
		
		boolean foundSpain = false;
		boolean foundAmerica = false;
		boolean foundCanada = false;
		
		for (Project p : allProjects) {
			if (!foundSpain) {
				foundSpain = areEqual(p, spain, false);
			}
			
			if (!foundAmerica) {
				foundAmerica = areEqual(p, america, false);
			}
			
			if (!foundCanada) {
				foundCanada = areEqual(p, canada, false);
			}
		}
		assertTrue(foundSpain && foundAmerica && foundCanada);
		
	}

	/**
	 * Test method for {@link server.database.ProjectDAO#update(shared.model.Project)}.
	 * @throws DatabaseException 
	 */
	@Test
	public void testUpdate() throws DatabaseException {
		
		Project spain = new Project("spain", 3, 10, 5);
		Project america = new Project("america", 1, 1, 1);
		
		dbProjects.add(spain);
		dbProjects.add(america);
		
		spain.setTitle("spanish");
		spain.setRecordsPerImage(50);
		spain.setFirstYCoord(50);
		spain.setRecordHeight(50);


		america.setTitle("american");
		america.setRecordsPerImage(2);
		america.setFirstYCoord(2);
		america.setRecordHeight(2);
		
		
		dbProjects.update(spain);
		dbProjects.update(america);
		
		List<Project> all = dbProjects.getAll();
		assertEquals(2, all.size());
		
		boolean foundSpain = false;
		boolean foundAmerica = false;
		
		for (Project p : all) {
			
			if (!foundSpain) {
				foundSpain = areEqual(p, spain, false);
			}		
			if (!foundAmerica) {
				foundAmerica = areEqual(p, america, false);
			}
		}
		
		assertTrue(foundSpain && foundAmerica);
	}

	/**
	 * Test method for {@link server.database.ProjectDAO#delete(shared.model.Project)}.
	 * @throws DatabaseException 
	 */
	@Test
	public void testDelete() throws DatabaseException {
		
		Project spain = new Project("spain", 3, 10, 5);
		Project america = new Project("america", 1, 1, 1);
		Project canada = new Project("canada", 5, 10, 40);
		Project mexico = new Project("mexico", 100, 100, 100);
		
		dbProjects.add(spain);
		dbProjects.add(america);
		dbProjects.add(canada);
		dbProjects.add(mexico);
		
		List<Project> allProjects = dbProjects.getAll();

		assertEquals(4, allProjects.size());
	
		dbProjects.delete(spain);
		dbProjects.delete(canada);
		dbProjects.delete(mexico);
		dbProjects.delete(america);
		
		allProjects = dbProjects.getAll();
		assertEquals(0, allProjects.size());
	}
	
	
	private boolean areEqual(Project a, Project b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}

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
