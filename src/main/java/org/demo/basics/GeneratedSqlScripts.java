package org.demo.basics;

import javax.persistence.Persistence;

public class GeneratedSqlScripts {

	 public static void main(String[] args) 
	 { 
		 Persistence.generateSchema("myunit", null); // New in JPA 2.1
		 
		 /*
		  Requires :
		  	<property name="javax.persistence.schema-generation.scripts.action"        value="drop-and-create"/>				
			<property name="javax.persistence.schema-generation.scripts.create-target" value="sampleCreate.ddl"/>
			<property name="javax.persistence.schema-generation.scripts.drop-target"   value="sampleDrop.ddl"/>
		  in persitence.xml
		  */
		 
		 // Persistence.createEntityManagerFactory("myunit") provoque aussi la génération des scripts
		 // Persistence.createEntityManagerFactory("myunit");
	 }
}
