package org.demo.test.util.jpa;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.nanoj.persistence.jpa.EntityManagerWrapper;
import org.nanoj.persistence.jpa.EntityManagerWrapperThreadLocal;
import org.nanoj.persistence.jpa.PersistenceFactory;
import org.nanoj.persistence.jpa.PersistenceManager;

/**
 * JUnit utility class for tests with JPA (test case ancestor) <br>
 * With this class an EntityManager is created before each test and closed after each test <br>
 * 
 * @author Laurent Guerin
 *
 */
public abstract class OneEntityManagerForEachTest {

	private static boolean LOGGING = true ;
	
	public static void setLogging(boolean flag) {
		LOGGING = flag ;
	}
	protected static void log(String msg) {
		if ( LOGGING ) {
			System.out.println("LOG: " + msg ) ; 
		}
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log("\n@BeforeClass : init PersistenceFactory ");
		
		// Just initialize the PersistenceFactory
		PersistenceFactory.init();
		log("PersistenceFactory ready " );		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		log("\n@AfterClass : nothing");
	}

	@Before
	public void setUp() throws Exception {
		// OPEN the EntityManager before each test : like "Session In View"
		log("");
		log("@Before ------------------------------------------------");
		log("Opening EntityManager ...");
		PersistenceManager.openEntityManager();
		log("EntityManager is open");
		log("@Before ------------------------------------------------");
	}

	@After
	public void tearDown() throws Exception {
		// CLOSE the EntityManager after each test : like "Session In View"
		log("@After ------------------------------------------------");
		log("Closing EntityManager ...");
		PersistenceManager.closeEntityManager();
		log("EntityManager is closed");
		log("@After ------------------------------------------------");
	}

	/**
	 * Returns the current EntityManager ( created previously before each test )
	 * @return
	 */
	protected EntityManager getEntityManager() {
		EntityManagerWrapper entityManagerWrapper = EntityManagerWrapperThreadLocal.get();
		if ( entityManagerWrapper != null ) {
			return entityManagerWrapper.getEntityManager();
		}
		else {
			throw new IllegalStateException("No current EntityManager");
		}
	}

}
