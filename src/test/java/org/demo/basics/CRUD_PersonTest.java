package org.demo.basics;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import org.demo.basics.entities.PersonEntity;
import org.demo.test.util.jpa.OneEntityManagerForEachTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;



@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class CRUD_PersonTest extends OneEntityManagerForEachTest {

	@Test
	public void test1_persist() {
		System.out.println("Test #1 : persist ...");
		EntityManager em = getEntityManager();
		PersonEntity person = new PersonEntity();
		person.setId(1);
		person.setFirstName("Luke");
		person.setLastName("Skywalker");
		
		em.getTransaction().begin();
		em.persist(person);
		em.getTransaction().commit();
		
		System.out.println("Test #1 : done.");
	}
	
	@Test
	public void test2_find() {
		System.out.println("Test #2 : find ...");
		EntityManager em = getEntityManager();
		
		PersonEntity person = em.find(PersonEntity.class, 1);
		if ( person != null ) {
			System.out.println("Found : " + person );
			System.out.println("getLastName : " + person.getLastName() );
			System.out.println("getLastName2 : " + person.getLastName2() ); 
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
		PersonEntity person = em.find(PersonEntity.class, 1);
		assertNotNull(person);
		person.setLastName2("foo");		
		em.getTransaction().commit();
		
		System.out.println("Test #3 : done.");
	}

	@Test
	public void test4_find() {
		System.out.println("Test #4 : find ...");
		EntityManager em = getEntityManager();
		
		PersonEntity person = em.find(PersonEntity.class, 1);
		assertNotNull(person);
		System.out.println("Found : " + person );
		System.out.println("getLastName : " + person.getLastName() );
		System.out.println("getLastName2 : " + person.getLastName2() );
		
		System.out.println("Test #2 : done.");
	}

	@Test
	public void test5_persist() {
		System.out.println("Test #5 : persist ...");
		EntityManager em = getEntityManager();
		PersonEntity person = new PersonEntity();
		person.setId(99);
		person.setFirstName("Luke");
		person.setLastName("Skywalker");
		
		em.getTransaction().begin();
		em.persist(person);
		em.getTransaction().commit();
		
		System.out.println("Test #5 : done.");
	}
}
