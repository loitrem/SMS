package jpa.service;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.SQLException;

public class CourseServiceTest {

    @BeforeAll // will run before mainrunnertest and must be static
    static void init(){
        System.out.println("Beginning of testing for CourseService Class");
    }

    @AfterAll // will run after mainrunnertest and must be static
    static void tearDown(){
        System.out.println("End of testing for CourseService Class");
    }

    @ParameterizedTest
    @CsvSource({
            "1,Anderea Scamaden,English",
            "5,Dani Swallow,Physics",
            "8,Carolan Stoller,Data Structures"
    })
    @DisplayName("getCourseByIdTrue method to check for problems")
    public void testGetCourseByIdTrue(ArgumentsAccessor arg) throws ArgumentAccessException, SQLException {
        //makes a new course
        Course expected = new Course();
        //puts supplied args into the new course
        expected.setCId(arg.getInteger(0));
        expected.setCInstructorName(arg.getString(1));
        expected.setCName(arg.getString(2));
        //makes a new course service
        CourseService testCourseService = new CourseService();
        //calls get course by id using supplied args
        Course actual = testCourseService.getCourseById(expected.getCId());
        //checks if instructor name and course name match supplied course id numbers
        Assertions.assertEquals(expected.getCInstructorName(), actual.getCInstructorName(),"This should return true");
        Assertions.assertEquals(expected.getCName(), actual.getCName(),"This should return true");
    }

    @ParameterizedTest
    @CsvSource({
            "1,Anderea Scamaden TEST,English TEST",
            "5,Dani Swallow TEST,Physics TEST",
            "8,Carolan Stoller TEST,Data Structures TEST"
    })
    @DisplayName("getCourseByIdFalse method to check for problems")
    public void testGetCourseByIdFalse(ArgumentsAccessor arg) throws ArgumentAccessException, SQLException {
        //makes a new course
        Course expected = new Course();
        //puts supplied args into the new course
        expected.setCId(arg.getInteger(0));
        expected.setCInstructorName(arg.getString(1));
        expected.setCName(arg.getString(2));
        //makes a new course service
        CourseService testCourseService = new CourseService();
        //calls get course by id using supplied args
        Course actual = testCourseService.getCourseById(expected.getCId());
        //checks if instructor name and course name match supplied course id numbers
        Assertions.assertNotEquals(expected.getCInstructorName(), actual.getCInstructorName(),"This should return true");
        Assertions.assertNotEquals(expected.getCName(), actual.getCName(),"This should return true");
    }
}
