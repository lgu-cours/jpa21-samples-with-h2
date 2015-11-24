package org.demo.basics.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

@Entity
@Table(name="PROFESSOR" )
@SqlResultSetMapping (
		name = "ProfWithDepartment",
		entities = {
			@EntityResult(entityClass = ProfessorEntity.class, 
			fields = {
				@FieldResult(name = "id",         column = "PROF_ID"), 
				@FieldResult(name = "name",       column = "PROF_NAME"), 
				@FieldResult(name = "salary",     column = "SALARY") ,
				@FieldResult(name = "department", column = "DEP_ID") 
			} ),
			@EntityResult(entityClass = DepartmentEntity.class, 
			fields = {
				@FieldResult(name = "id",     column = "DEP_ID"), 
				@FieldResult(name = "name",   column = "DEP_NAME")
			} )
		}
	)

public class ProfessorEntity {
	
	  @Id
	  @Column(name = "ID")
	  private int id;

	  @Column(name = "NAME", length=40)
	  private String name;

	  @Column(name = "SALARY")
	  private long salary;

	  @ManyToOne
	  @JoinColumn(name = "DEPT_ID")
	  private DepartmentEntity department;

	  public int getId() {
	    return id;
	  }

	  public void setId(int id) {
	    this.id = id;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public long getSalary() {
	    return salary;
	  }

	  public void setSalary(long salary) {
	    this.salary = salary;
	  }

	  public DepartmentEntity getDepartment() {
	    return department;
	  }

	  public void setDepartment(DepartmentEntity department) {
	    this.department = department;
	  }

	@Override
	public String toString() {
		return "ProfessorEntity [id=" + id + ", name=" + name + ", salary="
				+ salary + ", department=" + department  + "]";
	}

	  
}
