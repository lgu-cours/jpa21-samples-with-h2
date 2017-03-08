package org.demo.basics;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.demo.basics.dto.PersonDTO;
import org.demo.basics.entities.DepartmentEntity;
import org.demo.basics.entities.EmployeeEntity;
import org.demo.basics.entities.PersonEntity;
import org.demo.basics.entities.ProfessorEntity;
import org.demo.test.util.jpa.OneEntityManagerForEachTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class QueryNativeTest extends OneEntityManagerForEachTest {

	@Test
	public void test00_init_database() {
		EntityManager em = getEntityManager();
		System.out.println("Test #0 : init DB ...");
		for ( int i = 1 ; i <= 5 ; i++ ) {
			insertPersonData(em, i);
			insertEmployeeData(em, i);
		}
		for ( int i = 1 ; i <= 3 ; i++ ) {
			insertDepartmentData(em, i);
		}
		for ( int i = 1 ; i <= 10 ; i++ ) {
			insertProfessorData(em, i);
		}
		System.out.println("Test #0 : done.");
	}
	
	//------------------------------------------------------------------------------------
	@Test
	public void test01_mapping_all_to_object() {
		EntityManager em = getEntityManager();
		System.out.println("Test #1 : query ...");
		Query query = em.createNativeQuery("SELECT * FROM PERSON ", PersonEntity.class);
		
		@SuppressWarnings("unchecked")
		List<PersonEntity> list = query.getResultList();
		
		printList(list);
	}
	
	@Test
	public void test02_param_jdbc() {
		EntityManager em = getEntityManager();
		System.out.println("Test : query ...");
		Query query = em.createNativeQuery("SELECT * FROM PERSON WHERE ID >= ? AND ID <= ? ", PersonEntity.class);
		query.setParameter(1, 4);
		query.setParameter(2, 5);
		
		@SuppressWarnings("unchecked")
		List<PersonEntity> list = query.getResultList();
		
		printList(list);
	}

	@Test
	public void test03_param_numeric() {
		EntityManager em = getEntityManager();
		System.out.println("Test : query ...");
		Query query = em.createNativeQuery("SELECT * FROM PERSON WHERE ID >= ?11 AND ID <= ?22", PersonEntity.class);
		query.setParameter(11, 1);
		query.setParameter(22, 3);
		
		@SuppressWarnings("unchecked")
		List<PersonEntity> list = query.getResultList();
		
		printList(list);
	}
	
	@Test
	public void test04_param_named() {
		EntityManager em = getEntityManager();
		System.out.println("Test : query ...");
		Query query = em.createNativeQuery("SELECT * FROM PERSON WHERE ID >= :min AND ID <= :max", PersonEntity.class);
		query.setParameter("min", 2);
		query.setParameter("max", 4);
		
		@SuppressWarnings("unchecked")
		List<PersonEntity> list = query.getResultList();
		
		printList(list);
	}
	
	//------------------------------------------------------------------------------------

	@Test
	public void test11_mapping_integer_to_integer() {
		EntityManager em = getEntityManager();
		System.out.println("Test #11 : query ...");
		Query query = em.createNativeQuery("SELECT ID FROM PERSON " );
		
		@SuppressWarnings("unchecked")
		List<Integer> list = query.getResultList();
		
		System.out.println("Result list size = " + list.size());
		for ( Integer i : list ) {
			System.out.println(" . " + i);
		}	
	}
	
	@Test
	public void test12_mapping_columns_to_entity() {
		EntityManager em = getEntityManager();
		System.out.println("Test #12 : query ...");
		Query query = em.createNativeQuery("SELECT FIRST_NAME, LAST_NAME, ID FROM PERSON ", PersonEntity.class);
//		Query query = em.createNativeQuery("SELECT * FROM PERSON ", PersonEntity.class); // OK
//		Query query = em.createNativeQuery("SELECT LAST_NAME, ID FROM PERSON ", PersonEntity.class);
		
		// All the columns must be in the request
		// Query query = em.createNativeQuery("SELECT FIRST_NAME, ID FROM PERSON ", PersonEntity.class); 
		// Error : Column "LAST_NAME" not found
		
		@SuppressWarnings("unchecked")
		List<PersonEntity> list = query.getResultList();

		for ( PersonEntity p : list ) {
			assertTrue(em.contains(p));
		}
				
		printList(list);
	}

	@Test
	public void test14_mapping_all_to_integer() {
		EntityManager em = getEntityManager();
		System.out.println("Test : query ...");
		Query query = em.createNativeQuery("SELECT * FROM PERSON " );
		
//		List<?> list = query.getResultList();		// OK
//		System.out.println("Result list size = " + list.size());
//		for ( Object o : list ) {
//			System.out.println(" . " + o.getClass().getCanonicalName() ); // java.lang.Object[]
//		}	
		
		@SuppressWarnings("unchecked")
		List<Integer> list = query.getResultList();
		
		System.out.println("Result list size = " + list.size());
		for ( Object o : list ) {
			System.out.println(" . " + o.getClass().getCanonicalName() ); // java.lang.Object[]
		}	
//		for ( Integer i : list ) { // Error : class cast exception
//			System.out.println(" . " + i + " class : " + i.getClass().getCanonicalName() );
//		}	
		
	}

	@Test
	public void test15_mapping_all_to_object_array() {
		EntityManager em = getEntityManager();
		System.out.println("Test : query ...");
		Query query = em.createNativeQuery("SELECT * FROM PERSON " );
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		
		System.out.println("Result list size = " + list.size());
		for ( Object[] array : list ) {
			System.out.println(" . " + array[0] + " " + array[1] + " " + array[2]);
		}	
	}

	@Test 
	public void test16_mapping_pojo() {
		EntityManager em = getEntityManager();
		System.out.println("Test : query ...");
		
		Query query = em.createNativeQuery("SELECT ID, FIRST_NAME FROM PERSON ", "IdAndNameMapping" );
		
		@SuppressWarnings("unchecked")
		List<PersonDTO> list = query.getResultList();
		
		System.out.println("Result list size = " + list.size());
		for ( PersonDTO p : list ) {
			System.out.println(" . " + p.getId() + " " + p.getName() );
		}	
	}
	
	@Test 
	public void test20_mapping_with_SqlResultSetMapping() {
		EntityManager em = getEntityManager();
		System.out.println("Test : query ...");
		Query query = em.createNativeQuery("SELECT FIRST_NAME, LAST_NAME, ID FROM EMPLOYEE ", "EmployeeMapping" ); 
		//Query query = em.createNativeQuery("SELECT LAST_NAME, ID FROM EMPLOYEE ", "EmployeeMapping" ); // FIRST_NAME bot found
		
		@SuppressWarnings("unchecked")
		List<EmployeeEntity> list = query.getResultList();

		// All entities are in the Persistence Context
		for ( EmployeeEntity p : list ) {
			assertTrue(em.contains(p));
		}
		
		printList(list);
	}
	
	@Test 
	public void test22_mapping_join_with_SqlResultSetMapping_Columns() {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		
		DepartmentEntity dep1 = new DepartmentEntity();
		dep1.setId(1);
		dep1.setName("Department1");
		em.persist(dep1);

		ProfessorEntity prof1 = new ProfessorEntity();
		prof1.setId(1);
		prof1.setName("Prof1");
		prof1.setDepartment(dep1);
		
		em.persist(prof1);
		
		em.getTransaction().commit();
		
		System.out.println("Test : query ...");
		
		String SQL = "SELECT " 
				+ " PROF.ID      AS PROF_ID, "
				+ " PROF.NAME    AS PROF_NAME, "
				+ " DEP.NAME     AS DEP_NAME"
				+ " FROM "
				+ " PROFESSOR  PROF, "
				+ " DEPARTMENT DEP "
				+ " WHERE PROF.DEPT_ID = DEP.ID " ;
		
		// The query returns a list of Object[], where each entry contains an object “Person” at position 0, and a object “Car” at the position 1.
		// All entities are in the Persistence Context
		
				
		Query query = em.createNativeQuery(SQL, "ProfNameAndDeptName" ); 
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		for ( Object[] array : list ) {
			for ( Object o : array ) {
				System.out.println(" * " + o.toString() );
			}
		}

	
	}

	@Test 
	public void test23_mapping_join_with_SqlResultSetMapping_Entities() {
		EntityManager em = getEntityManager();
		System.out.println("Test : query ...");
		String SQL = "SELECT " 
				+ " PROF.ID      AS PROF_ID, "
				+ " PROF.NAME    AS PROF_NAME, "
				+ " PROF.SALARY, "
				+ " DEP.ID       AS DEP_ID,"
				+ " DEP.NAME     AS DEP_NAME"
				+ " FROM "
				+ " PROFESSOR  PROF, "
				+ " DEPARTMENT DEP "
				+ " WHERE PROF.DEPT_ID = DEP.ID " ;
		
//		String SQL = "SELECT " 
//				+ " PROF.ID   AS PROF_ID, "
//				+ " PROF.NAME AS PROF_NAME, "
//				+ " PROF.SALARY, "
//				+ " DEP.ID    AS DEP_ID,"
//				+ " DEP.NAME  AS DEP_NAME"
//				+ " FROM "
//				+ " PROFESSOR  PROF "
//				+ " JOIN DEPARTMENT DEP "
//				+ " ON PROF.DEPT_ID = DEP.ID " ;

		// The query returns a list of Object[], where each entry contains an object “Person” at position 0, and a object “Car” at the position 1.
		// All entities are in the Persistence Context
		Query query = em.createNativeQuery(SQL, "ProfWithDepartment" ); 
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		for ( Object[] array : list ) {
			System.out.println("--- ");
			for ( Object o : array ) {
				System.out.println(" * " + o.toString() );
				// Check all entities are in the persistence context
				assertTrue(em.contains(o)); 
			}
		}
	}

	//------------------------------------------------------------------------------------

	private void insertPersonData(EntityManager em, int id) {
		PersonEntity person = new PersonEntity();
		person.setId(id);
		person.setFirstName("Luke" + id);
		person.setLastName("Skywalker"+ id);
		em.getTransaction().begin();
		em.persist(person);
		em.getTransaction().commit();
		System.out.println("data inserted");
	}
	
	private void insertEmployeeData(EntityManager em, int id) {
		EmployeeEntity emp = new EmployeeEntity();
		emp.setId(id);
		emp.setFirstName("Dark" + id);
		emp.setLastName("Vador"+ id);
		em.getTransaction().begin();
		em.persist(emp);
		em.getTransaction().commit();
		System.out.println("data inserted");
	}
	
	private void insertDepartmentData(EntityManager em, int id) {
		DepartmentEntity dep = new DepartmentEntity();
		dep.setId(id);
		dep.setName("Department " + id);
		em.getTransaction().begin();
		em.persist(dep);
		em.getTransaction().commit();
		System.out.println("data inserted");
	}

	private void insertProfessorData(EntityManager em, int id) {
		DepartmentEntity dep1 = new DepartmentEntity();
		dep1.setId(1);
		dep1.setName("Dept 1");
		
		ProfessorEntity prof = new ProfessorEntity();
		prof.setId(id);
		prof.setName("Tournesol " + id);
		prof.setSalary(1000 + id );
		prof.setDepartment(dep1);
		em.getTransaction().begin();
		em.persist(prof);
		em.getTransaction().commit();
		System.out.println("data inserted");
	}
	
	private void printList(List<?> list) {
		System.out.println("Result list size = " + list.size() );
		for ( Object o : list ) {
			System.out.println(" . " + o.toString() );
		}
	}
//	private void printList(List<PersonEntity> list) {
//		System.out.println("Result list size = " + list.size());
//		for ( PersonEntity p : list ) {
//			System.out.println(" . " + p);
//		}
//	}
//	private void printEmployeesList(List<EmployeeEntity> list) {
//		System.out.println("Result list size = " + list.size());
//		for ( EmployeeEntity e : list ) {
//			System.out.println(" . " + e);
//		}
//	}

}
