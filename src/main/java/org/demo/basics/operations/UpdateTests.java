package org.demo.basics.operations;

import javax.persistence.EntityManager;

import org.demo.basics.entities.CountryEntity;

public class UpdateTests {

	private static void log(String msg) {
		System.out.println("[LOG] : " + msg);
	}

	public static void main(String[] args) {
		EntityManager em = Helper.init();
		log("Ready...");
		log("-----");

		log("EntityManager class : " + em.getClass().getCanonicalName() );

		CountryEntity country = new CountryEntity();
		country.setCode("FR");
		country.setName("France");
		create(em, country );

		CountryEntity countryFound = find(em, "FR" );

		if ( countryFound == country ) {
			log("Same instance"); // Yes, same instance
		}
		else {
			log("Not the same instance");
		}
		
		testUpdate1(em);
		testUpdate2(em);
		
		Helper.finished(em);
	}

	public static void create(EntityManager em, CountryEntity country ) {
		log("----- create ( " + country + " ) ...");
		em.getTransaction().begin();		 
		log("persist...");
		em.persist(country);
		log("commit..." );
		em.getTransaction().commit();
	}

	public static CountryEntity find(EntityManager em, String code ) {
		log("----- findCountry ( code = " + code + " ) ...");
		CountryEntity country = em.find(CountryEntity.class, code);
		if ( country != null ) {
			log("Found : " + country );
		}
		else {
			log("Not found");
		}
		log("em.contains ? " + em.contains(country) );
		return country;
	}
	 
	public static void testPersist0(EntityManager em ) {
		log("----- testPersist0() ...");
		CountryEntity country = new CountryEntity();
		country.setCode("00");
		country.setName("00 country");
		log("Country : " + country );
		country.setName("00 country 2");
		
		log("persist...");
		em.persist(country);
		log("begin..." );
		em.getTransaction().begin();		 
		log("commit..." );
		em.getTransaction().commit();
		
		log("find : " + em.find(CountryEntity.class, "00") );
	}
	
	
	public static void testUpdate1(EntityManager em ) {
		log("----- testUpdate1 ...");

		CountryEntity country = em.find(CountryEntity.class, "FR");
		
		if ( country != null ) {
			log("begin..." );
			em.getTransaction().begin();		 

			country.setName("France (updated)");
			log("Updated in memory : " + country );
			
			log("commit..." );
			em.getTransaction().commit(); // SQL UPDATE
		}
		else {
			log("Not found.");
		}

		log("find : " + em.find(CountryEntity.class, "FR") );
	}

	public static void testUpdate2(EntityManager em ) {
		log("----- testUpdate2 ...");

		// Create a new entity 
		CountryEntity country = new CountryEntity();
		country.setCode("UK");
		country.setName("United Kingdom");
		log("New entity : " + country );
		
		em.getTransaction().begin();		 
		log("persist : " + country);
		em.persist(country); 
		em.getTransaction().commit(); // SQL INSERT
		
		// Update this new entity 
		log("begin..." );
		em.getTransaction().begin();	
		
		country.setName("United Kingdom (updated)");
		log("Updated in memory : " + country );
			
		log("commit..." );
		em.getTransaction().commit(); // SQL UPDATE

		log("find : " + em.find(CountryEntity.class, "UK") );
	}

	
}
