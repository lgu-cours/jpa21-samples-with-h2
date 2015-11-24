/*
 * Created on 21 janv. 2015 ( Time 17:25:52 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
// This Bean has a basic Primary Key (not composite) 

package org.demo.basics.dto;



public class PersonDTO {

    private Integer    id      ;

    private String     name    ;

    public PersonDTO(Integer id , String name  ) {
        this.id = id ;
        this.name = name;
    }
    
    //----------------------------------------------------------------------
    public void setId( Integer id ) {
        this.id = id ;
    }
    public Integer getId() {
        return this.id;
    }

    //----------------------------------------------------------------------
    public void setName( String name ) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        sb.append(name);
        return sb.toString(); 
    } 
}