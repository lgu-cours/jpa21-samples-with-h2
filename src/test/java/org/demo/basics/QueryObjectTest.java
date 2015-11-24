package org.demo.basics;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.demo.basics.entities.PersonEntity;
import org.demo.test.util.jpa.OneEntityManagerForEachTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class QueryObjectTest extends OneEntityManagerForEachTest {

	@Test
	public void test00_init_database() {
		EntityManager em = getEntityManager();
		System.out.println("Test #0 : init DB ...");
		for ( int i = 1 ; i <= 50 ; i++ ) {
			insertData(em, i);
		}
		System.out.println("Test #0 : done.");
	}
	
	@Test
	public void test01_count_native_sql() {
		EntityManager em = getEntityManager();
		System.out.println("Test #1 : query ...");
		Query query = em.createNativeQuery("SELECT COUNT(*) FROM PERSON " );
		
		Object result = query.getSingleResult();
		System.out.println("Result class : " + result.getClass().getCanonicalName() ); // java.math.BigInteger
		System.out.println("Class : " +  query.getSingleResult().getClass().getCanonicalName() );
		BigInteger count = (BigInteger) result ;
//		Long count = (Long) result ; // ERROR : cannot cast  
		System.out.println("COUNT = " + count );
		assertEquals(50, count.intValue());
	}

	@Test
	public void test01_count_jpql() {
		EntityManager em = getEntityManager();
		System.out.println("Test #1 : COUNT JPQL ...");
		Query query = em.createQuery("SELECT COUNT(p) FROM PersonEntity p " );
		
		Object result = query.getSingleResult();
		System.out.println("Result class : " + result.getClass().getCanonicalName() ); // java.math.BigInteger
		System.out.println("Class : " +  query.getSingleResult().getClass().getCanonicalName() );
		Long count = (Long) result ;
//		Long count = (Long) result ; // ERROR : cannot cast  
		System.out.println("COUNT = " + count );
		assertEquals(50, count.intValue());
	}

	@Test
	public void test01_setMaxResults() {
		EntityManager em = getEntityManager();
		System.out.println("Test #1 : query ...");
		Query query = em.createNativeQuery("SELECT * FROM PERSON ", PersonEntity.class);
		
		query.setMaxResults(10);
		
		@SuppressWarnings("unchecked")
		List<PersonEntity> list = query.getResultList();

		printList(list);
		assertEquals(10, list.size());
	}

	@Test
	public void test02_setFirstResult() {
		EntityManager em = getEntityManager();
		System.out.println("Test #1 : query ...");
		Query query = em.createNativeQuery("SELECT * FROM PERSON ", PersonEntity.class);
		
		query.setFirstResult(10); // 0 to 9, 10 to 19, etc 
		
		@SuppressWarnings("unchecked")
		List<PersonEntity> list = query.getResultList();

		printList(list);
		assertEquals(40, list.size());
	}

	@Test
	public void test99_page1() {
		List<PersonEntity> list = getPage(1);
		printList(list);
		assertEquals(20, list.size());
	}
	@Test
	public void test99_page2() {
		List<PersonEntity> list = getPage(2);
		printList(list);
		assertEquals(20, list.size());
	}
	@Test
	public void test99_page3() {
		List<PersonEntity> list = getPage(3);
		printList(list);
		assertEquals(10, list.size());
	}
	@Test(expected=IllegalArgumentException.class)
	public void test99_page0() {
		List<PersonEntity> list = getPage(0);
		printList(list);
		assertEquals(10, list.size());
	}
	@Test
	public void test99_page4() {
		List<PersonEntity> list = getPage(4);
		printList(list);
		assertEquals(0, list.size());
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

	private List<PersonEntity> getPage(int pageNumber) {
		return getPageSQL(pageNumber);
		//return getPageJPQL(pageNumber);
	}
	
	private List<PersonEntity> getPageSQL(int pageNumber) {
		int pageSize = 20 ;
		EntityManager em = getEntityManager();
		System.out.println("Test #1 : query ...");
		Query query = em.createNativeQuery("SELECT * FROM PERSON ", PersonEntity.class);
		
		// Sets the maximum number of entities that should be included in the page
		query.setMaxResults(pageSize);
		
		int offset = ( pageNumber - 1 ) * pageSize ; // 0, 20, 40, 60, ...
		// Sets the offset position in the result set to start pagination
		query.setFirstResult(offset); // 0 to 9, 10 to 19, etc 
		
		@SuppressWarnings("unchecked")
		List<PersonEntity> list = query.getResultList();
		
		return list ;
	}
	
	private List<PersonEntity> getPageJPQL(int pageNumber) {
		int pageSize = 20 ;
		EntityManager em = getEntityManager();
		System.out.println("Test #1 : query ...");
		Query query = em.createQuery("SELECT p FROM PersonEntity p ");
		
		// Sets the maximum number of entities that should be included in the page
		query.setMaxResults(pageSize);
		
		int offset = ( pageNumber - 1 ) * pageSize ; // 0, 20, 40, 60, ...
		// Sets the offset position in the result set to start pagination
		query.setFirstResult(offset); // 0 to 9, 10 to 19, etc 
		
		@SuppressWarnings("unchecked")
		List<PersonEntity> list = query.getResultList();
		
		return list ;
	}
}
