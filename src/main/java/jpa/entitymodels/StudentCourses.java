/*
 * Filename: StudentCourses.java
* Author: Stefanski
* 02/25/2020 
 */
package jpa.entitymodels;

import lombok.*;

import javax.persistence.*;

/**
 * @author Harry
 *
 */
@Getter @Setter
@Entity
@Table( name="StudentCourses")
@IdClass( StudentCoursesID.class)
@NamedQueries({
	// created named queries
	@NamedQuery( name="CoursesByStudent", query="SELECT c FROM StudentCourses c WHERE c.eMail = :email"),
	@NamedQuery( name="Find all studentcourses", query="Select c from StudentCourses c")

})

public class StudentCourses {

	//@Id makes email and course id primary key together
	@Id
	//rename in database
	@Column(name="student_email")
	private String eMail;

	//no args constructor
	public StudentCourses() {}

	/**
	 * @param eMail
	 * @param courseID
	 */
	// all args constructor
	public StudentCourses(String eMail, int courseID) {
		this.eMail = eMail;
		this.courseID = courseID;
	}

	@Id
	//rename in databse
	@Column(name="course_id")
	private int courseID;


// getters and setters for all variables

	/**
	 * @return the eMail
	 */
	public String geteMail() {
		return eMail;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * @return the courseID
	 */
	public int getCourseID() {
		return courseID;
	}

	/**
	 * @param courseID the courseID to set
	 */
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	// both these methods are to avoid the exception created with .equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courseID;
		result = prime * result + ((eMail == null) ? 0 : eMail.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentCourses other = (StudentCourses) obj;
		if (courseID != other.courseID)
			return false;
		if (eMail == null) {
			if (other.eMail != null)
				return false;
		} else if (!eMail.equals(other.eMail))
			return false;
		return true;
	}

}
