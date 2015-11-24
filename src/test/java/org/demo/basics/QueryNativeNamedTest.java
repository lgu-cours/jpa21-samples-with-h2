package org.demo.basics;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.demo.basics.entities.PersonEntity;
import org.demo.test.util.jpa.OneEntityManagerForEachTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;



@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class QueryNativeNamedTest extends OneEntityManagerForEachTest {

	@Test
	public void test0() {
		EntityManager em = getEntityManager();
		System.out.println("Test #0 : init DB ...");
		for ( int i = 1 ; i <= 5 ; i++ ) {
			insertData(em, i);
		}
		System.out.println("Test #0 : done.");
	}
	
	@Test
	public void test1() {
		EntityManager em = getEntityManager();
		System.out.println("Test #1 : named query ...");
		Query query = em.createNamedQuery("Person.maxId") ;
		Integer maxId = (Integer) query.getSingleResult();
		System.out.println("Max id = " + maxId );
	}
	
	@Test
	public void test2() {
		EntityManager em = getEntityManager();
		System.out.println("Test #2 : named query ...");
		Query query = em.createNamedQuery("Person.selection") ;
		query.setParameter("min", 2);
		query.setParameter("max", 3);
		@SuppressWarnings("unchecked")
		List<PersonEntity> list = query.getResultList();
		printList(list);
	}
	
	private void insertData(EntityManager em, int id) {
		PersonEntity person = new PersonEntity();
		person.setId(id);
		person.setFirstName("Luke" + id);
		person.setLastName("Skywalker"+ id);
		em.getTransaction().begin();
		em.persist(person);
		em.getTransaction().commit();
		System.out.println("data inserted");
	}
	

	private void printList(List<PersonEntity> list) {
		System.out.println("Result list size = " + list.size());
		for ( PersonEntity p : list ) {
			System.out.println(" . " + p);
		}
	}
}
