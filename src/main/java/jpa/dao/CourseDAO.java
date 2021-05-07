package jpa.dao;

import jpa.entitymodels.Course;

import java.util.List;

public interface CourseDAO {
    // all methods to be implemented
    List<Course> getAllCourses();
    Course getCourseById(int id);
}
