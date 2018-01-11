package org.demo.basics.operations;

import javax.persistence.EntityManager;

import org.demo.basics.entities.CountryEntity;

public class PersistTests {

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
		
		testPersist0(em);
		testPersist1(em);
		testPersist2(em);
		testPersist3(em);
		testPersist4(em);
		testPersist5(em);
		
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
	
	public static void testPersist1(EntityManager em ) {
		log("----- testPersist1 () ...");
		CountryEntity country = new CountryEntity();
		country.setCode("ZZ");
		country.setName("ZZ country");
		log("Country : " + country );
		
		log("begin..." );
		em.getTransaction().begin();		 

		log("persist...");
		em.persist(country);
		
		log("persist...");
		em.persist(country);
		
		log("persist...");
		em.persist(country);
		
		log("persist...");
		em.persist(country);
		
		log("commit..." );
		em.getTransaction().commit();
	}

	public static void testPersist2(EntityManager em ) {
		log("----- testPersist2 () ...");
		
		CountryEntity country1 = new CountryEntity();
		country1.setCode("AA");
		country1.setName("AA country 1");


		log("begin..." );
		em.getTransaction().begin();		 

		log("persist : " + country1);
		em.persist(country1); 

		// Expected error
		CountryEntity country2 = new CountryEntity();
		country2.setCode("AA");
		country2.setName("AA country 2");
//		log("persist : " + country2);
//		em.persist(country2); // ERROR : same key => duplicated
		
		log("commit..." );
		em.getTransaction().commit();
	}

	public static void testPersist3(EntityManager em ) {
		log("----- testPersist3 () ...");
		
		CountryEntity country = new CountryEntity();
		country.setCode("BB");
		country.setName("BB country");

		log("begin..." );
		em.getTransaction().begin();		 

		log("persist : " + country);
		em.persist(country); 

		// country.setCode("CC"); // ERROR / commit : "identifier of an instance of ... was altered
		country.setName("BB country 2");
		em.persist(country); 
		country.setName("BB country 3");
		em.persist(country); 
		
		// Expected error
		log("commit..." );
		em.getTransaction().commit();  // INSERT + UPDATE

		log("find : " + em.find(CountryEntity.class, "BB") );
	}

	public static void testPersist4(EntityManager em ) {
		log("----- testPersist4 () ...");
		
		CountryEntity country = new CountryEntity();
		country.setCode("DD");
		country.setName("DD country 1");

		log("begin..." );
		em.getTransaction().begin();		 

		log("persist : " + country);
		em.persist(country); 

		country.setName("DD country 2");
		em.persist(country); 
		
		country.setName("DD country 3");
		em.persist(country); 
		
		// Expected error
		log("commit..." );
		em.getTransaction().commit();  // INSERT + UPDATE

		log("find : " + em.find(CountryEntity.class, "DD") );
	}

	public static void testPersist5(EntityManager em ) {
		log("----- testPersist5() ...");
		
		CountryEntity country = new CountryEntity();
		country.setCode("EE");
		country.setName("EE country 1");

		log("begin..." );
		em.getTransaction().begin();		 

		log("contains ? : " + em.contains(country) ); 

		log("persist : " + country);
		em.persist(country); 
		log("contains ? : " + em.contains(country) ); 

		log("remove : " + country);
		em.remove(country); 
		log("contains ? : " + em.contains(country) ); 

		log("persist : " + country);
		em.persist(country); 
		log("contains ? : " + em.contains(country) ); 
		
		// Expected error
		log("commit..." );
		em.getTransaction().commit();  // INSERT + UPDATE

		log("find : " + em.find(CountryEntity.class, "EE") );
	}

}
