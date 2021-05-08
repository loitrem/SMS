package jpa.dao;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.entitymodels.StudentCourses;

import java.util.List;

public interface StudentDAO {
    // all methods to be implemented
    List<Student> getAllStudents();
    Student getStudentByEmail(String email);
    boolean validateStudent(String email, String pass);
    void registerStudentToCourse(String email, int cId);
    List<StudentCourses> getStudentCourses(String email);
}
