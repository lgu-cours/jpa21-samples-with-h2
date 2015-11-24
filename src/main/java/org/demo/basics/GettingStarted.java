package org.demo.basics;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.demo.basics.entities.PersonEntity;

public class GettingStarted {

	 public static void main(String[] args) 
	 { 
		 System.out.println("--- Persistence.createEntityManagerFactory(xx)...");
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("myunit"); 

		 insert(emf) ;
		 load(emf) ;
		 
		 System.out.println("--- closing EntityManagerFactory...");
		 emf.close();		
	 }
	 
	 private static void insert(EntityManagerFactory emf) {
		 System.out.println("\n----- INSERT...");
		 EntityManager em = emf.createEntityManager(); 

		 
		 PersonEntity person1 = new PersonEntity();
		 person1.setId(1);
		 person1.setFirstName("Jules");
		 person1.setLastName("Verne");
		 em.getTransaction().begin();
		 em.persist(person1);
		 em.remove(person1);
		 em.persist(person1);
		 em.getTransaction().commit();

		 //em.flush(); // If not in a transaction : TransactionRequiredException: no transaction is in progress
		 
		 em.close();  // If not in a transaction : does noting (no error)
		 System.out.println("----------");
	 }

	 private static void load(EntityManagerFactory emf) {
		 System.out.println("\n----- LOAD...");
		 EntityManager em = emf.createEntityManager(); 

		 PersonEntity person = em.find(PersonEntity.class, 1);
		 if ( person != null ) {
			 System.out.println("Found : " + person );
		 }
		 else {
			 System.out.println("Not found");
		 }

		 em.close(); 
		 System.out.println("----------");
	 }
}
