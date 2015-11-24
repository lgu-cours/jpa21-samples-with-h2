package org.demo.basics;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;

import org.demo.basics.entities.EmployeeEntity;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.nanoj.persistence.jpa.PersistenceFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class EntityManagerCloseTest {


	private final static int ID = 1 ;
	
	@Test @Ignore
	public void test0() {
		System.out.println("Test #0 : createEntityManager()  ...");
		EntityManager em = PersistenceFactory.createEntityManager();
		assertNotNull(em);
		
		System.out.println("Test #0 : begin ");
		em.getTransaction().begin();

		EmployeeEntity employee = new EmployeeEntity();
		employee.setId(ID);
		employee.setFirstName("Luke");
		employee.setLastName("Skywalker");
		
		System.out.println("Test #0 : persist ");
		em.persist(employee);
		
		System.out.println("Test #0 : commit.");
		em.getTransaction().commit();

		System.out.println("Test #0 : close.");
		em.close();
		
		System.out.println("Test #0 : END.");

		em = PersistenceFactory.createEntityManager();
		employee = em.find(EmployeeEntity.class, ID);
		if ( employee != null ) {
			System.out.println("Found : " + employee );
		}
		else {
			System.out.println("Not found");
		}
		assertNull(employee); // Not found due to CLOSE

		assertNotNull(em);
	}
	
	@Test
	public void test1() {
		System.out.println("Test #1 : createEntityManager()  ...");
		EntityManager em = PersistenceFactory.createEntityManager();
		assertNotNull(em);
		
		System.out.println("Test #1 : begin ");
		em.getTransaction().begin();

		EmployeeEntity employee = new EmployeeEntity();
		employee.setId(ID);
		employee.setFirstName("Luke");
		employee.setLastName("Skywalker");
		
		System.out.println("Test #1 : persist ");
		em.persist(employee);
		
		System.out.println("Test #1 : flush.");
		em.flush();
//		System.out.println("Test #1 : commit.");
//		em.getTransaction().commit();

		System.out.println("Test #1 : CLOSE.");
		em.close();
		
		System.out.println("Test #1 : END.");

		em = PersistenceFactory.createEntityManager();
		System.out.println("Test #1 : find");
		employee = em.find(EmployeeEntity.class, ID);
		if ( employee != null ) {
			System.out.println("Found : " + employee );
		}
		else {
			System.out.println("Not found");
		}
		assertNull(employee); // Not found due to CLOSE without COMMIT even with FLUSH (found if commit)

		assertNotNull(em);
	}
	
}
