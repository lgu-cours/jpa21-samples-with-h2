package org.demo.basics;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;

import org.demo.basics.entities.EmployeeEntity;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.nanoj.persistence.jpa.PersistenceFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class EntityManagerFlushTest {


	@Test
	public void test1() {
		System.out.println("Test #1 : createEntityManager()  ...");
		EntityManager em = PersistenceFactory.createEntityManager();
		assertNotNull(em);
		
		System.out.println("Test #1 : persist ...");
		em.getTransaction().begin();

		EmployeeEntity employee = new EmployeeEntity();
		employee.setId(1);
		employee.setFirstName("Luke");
		employee.setLastName("Skywalker");
		
		em.persist(employee);
		
		em.flush();
		
		em.getTransaction().commit();
		System.out.println("Test #1 : done.");
	}
	
	@Test
	public void test2() {
		System.out.println("Test #2 : createEntityManager()  ...");
		EntityManager em = PersistenceFactory.createEntityManager();
		assertNotNull(em);
		
		System.out.println("Test #2 : begin transaction ...");
		em.getTransaction().begin();

		EmployeeEntity employee = new EmployeeEntity();
		employee.setId(2);
		employee.setFirstName("Homer");
		employee.setLastName("Simpson");
		
		System.out.println("Test #2 : persist ...");
		em.persist(employee);
		
		System.out.println("Test #2 : flush ...");
		em.flush(); // FLUSH : execute the INSERT command
		
		System.out.println("Test #2 : rollback transaction ...");
		em.getTransaction().rollback();
		
		employee = em.find(EmployeeEntity.class, 2);
		if ( employee != null ) {
			System.out.println("Found : " + employee );
		}
		else {
			System.out.println("Not found");
		}
		assertNull(employee); // Not found due to rollback
		
		System.out.println("Test #2 : done.");
	}
	
}
