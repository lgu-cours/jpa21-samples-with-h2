package org.demo.basics.operations;

import javax.persistence.EntityManager;

import org.demo.basics.entities.CountryEntity;

public class FindTests {

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
		
		CountryEntity countryUK = new CountryEntity();
		countryUK.setCode("UK");
		countryUK.setName("United Kingdom");
		create(em, countryUK );

		em.clear();
		
		CountryEntity countryFound = find(em, "FR" );

		if ( countryFound == country ) {
			log("Same instance"); // Yes, same instance
		}
		else {
			log("Not the same instance");
		}

		countryFound = find(em, "FR" );
		countryFound = find(em, "UK" );
		countryFound = find(em, "ZZ" );
		countryFound = find(em, "UK" );
		
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
	 
}
