package org.demo.basics;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import org.demo.basics.entities.EmployeeEntity;
import org.demo.test.util.jpa.OneEntityManagerForEachTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class CRUD_EmployeeTest extends OneEntityManagerForEachTest {

//	public CRUD_EmployeeTest() {
//	}
//	
//	protected EntityManager getEntityManager() {
//		EntityManagerWrapper entityManagerWrapper = EntityManagerWrapperThreadLocal.get();
//		if ( entityManagerWrapper != null ) {
//			return entityManagerWrapper.getEntityManager();
//		}
//		else {
//			throw new IllegalStateException("No current EntityManager");
//		}
//	}
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		System.out.println("\n@BeforeClass / setUpBeforeClass");
//		
//		// Just initialize the PersistenceFactory
//		PersistenceFactory.init();
//		System.out.println("PersistenceFactory ready " );		
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//		System.out.println("\n@AfterClass / tearDownAfterClass");
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		// OPEN the EntityManager before each test : like "Session In View"
//		System.out.println("");
//		System.out.println("@Before ------------------------------------------------");
//		System.out.println("Opening EntityManager ...");
//		PersistenceManager.openEntityManager();
//		System.out.println("EntityManager is open");
//		System.out.println("@Before ------------------------------------------------");
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		// CLOSE the EntityManager after each test : like "Session In View"
//		System.out.println("@After ------------------------------------------------");
//		System.out.println("Closing EntityManager ...");
//		PersistenceManager.closeEntityManager();
//		System.out.println("EntityManager is closed");
//		System.out.println("@After ------------------------------------------------");
//	}

	@Test
	public void test1_persist() {
		System.out.println("Test #1 : persist ...");
		EntityManager em = getEntityManager();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setId(1);
		employee.setFirstName("Luke");
		employee.setLastName("Skywalker");
		
		em.getTransaction().begin();
		em.persist(employee);
		em.getTransaction().commit();
		
		System.out.println("Test #1 : done.");
	}
	
	@Test
	public void test2_find() {
		System.out.println("Test #2 : find ...");
		EntityManager em = getEntityManager();
		
		EmployeeEntity employee = em.find(EmployeeEntity.class, 1);
		if ( employee != null ) {
			System.out.println("Found : " + employee );
			System.out.println("getLastName : " + employee.getLastName() );
		}
		else {
			System.out.println("Not found" );
		}
		
		System.out.println("Test #2 : done.");
	}
	
	@Test
	public void test3_update() {
		System.out.println("Test #3 : update ...");
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		EmployeeEntity employee = em.find(EmployeeEntity.class, 1);
		assertNotNull(employee);
		employee.setFirstName("Homer");
		employee.setLastName("Simpson");
		em.getTransaction().commit();
		
		System.out.println("Test #3 : done.");
	}

	@Test
	public void test4_find() {
		System.out.println("Test #4 : find ...");
		EntityManager em = getEntityManager();
		
		EmployeeEntity employee = em.find(EmployeeEntity.class, 1);
		assertNotNull(employee);
		System.out.println("Found : " + employee );
		System.out.println("getLastName : " + employee.getLastName() );
		
		System.out.println("Test #2 : done.");
	}

}
