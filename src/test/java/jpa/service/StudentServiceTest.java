package jpa.service;

import jpa.entitymodels.Student;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.*;
import org.junit.jupiter.params.provider.*;
import java.sql.SQLException;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)// per class will instantiate once per class vs once for every method
public class StudentServiceTest {

    @BeforeAll // will run before mainrunnertest and must be static
    static void init(){
        System.out.println("Beginning of testing for StudentService Class");
    }

    @AfterAll // will run after mainrunnertest and must be static
    static void tearDown(){
        System.out.println("End of testing for StudentService Class");
    }

    // make it a parameterized test
    @ParameterizedTest
    // parameter sources
    @CsvSource({
            "aiannitti7@is.gd,TWP4hf5j",
            "qllorens2@howstuffworks.com,W6rJuxd"
    })
    @DisplayName("validateStudentTrue method to check for problems validateStudent - all should return true")
    public void testValidateStudentTrue(ArgumentsAccessor arg){
        //make a new student
        Student expected = new Student();
        //sets parameters from args to new student
        expected.setSEmail(arg.getString(0));
        expected.setSPass(arg.getString(1));

        //new student services to call validateStudent
        StudentService testStudentService = new StudentService();
        //tests validateStudent which should return true if all arguement are correct
        Assertions.assertTrue(testStudentService.validateStudent(expected.getSEmail(), expected.getSPass()),"This should return true");
    }

    // make it a parameterized test
    @ParameterizedTest
    // parameter sources
    @CsvSource({
            "fake@is.gd,TWP4hf5j",
            "test@test.com,ThisWillBeFalse11"
    })
    @DisplayName("validateStudentFalse method to check for problems with validateStudent")
    public void testValidateStudentFalse(ArgumentsAccessor arg){
        //make a new student
        Student expected = new Student();
        //sets parameters from args to new student
        expected.setSEmail(arg.getString(0));
        expected.setSPass(arg.getString(1));

        //new student services to call validateStudent
        StudentService testStudentService = new StudentService();
        //tests validateStudent which should return true if all arguement are correct
        Assertions.assertFalse(testStudentService.validateStudent(expected.getSEmail(), expected.getSPass()),"This should return true");
    }

    @ParameterizedTest
    @CsvSource({
            "aiannitti7@is.gd,Alexandra Iannitti,TWP4hf5j",
            "qllorens2@howstuffworks.com,Quillan Llorens,W6rJuxd",
            "sbowden1@yellowbook.com,Sonnnie Bowden,SJc4aWSU"
    })
    @DisplayName("getStudentByEmailTrue method to check for problems")
    public void testGetStudentByEmailTrue(ArgumentsAccessor arg) throws ArgumentAccessException, SQLException {
        //makes new student
        Student expected = new Student();
        //puts args into new student
        expected.setSEmail(arg.getString(0));
        expected.setSName(arg.getString(1));
        expected.setSPass(arg.getString(2));
        //makes new student service
        StudentService testStudentService = new StudentService();
        //calls getStudentByEmail using provided email
        Student actual = testStudentService.getStudentByEmail(arg.getString(0));
        //tests to see if name and password match what is in database
        Assertions.assertEquals(expected.getSName(), actual.getSName(),"This should return true");
        Assertions.assertEquals(expected.getSPass(), actual.getSPass(),"This should return true");
    }
    @ParameterizedTest
    @CsvSource({
            "aiannitti7@is.gd,Alexandra Iannitti test,TWP4hf5j test",
            "qllorens2@howstuffworks.com,made upName,W6rJuxd test",
            "sbowden1@yellowbook.com,Sonnnie test Bowden,notRealP@ss"
    })
    @DisplayName("getStudentByEmailFalse method to check for problems")
    public void testGetStudentByEmailFalse(ArgumentsAccessor arg) throws ArgumentAccessException, SQLException {
        //makes new student
        Student expected = new Student();
        //puts args into new student
        expected.setSEmail(arg.getString(0));
        expected.setSName(arg.getString(1));
        expected.setSPass(arg.getString(2));
        //makes new student service
        StudentService testStudentService = new StudentService();
        //calls getStudentByEmail using provided email
        Student actual = testStudentService.getStudentByEmail(arg.getString(0));
        //tests to see if name and password match what is in database
        Assertions.assertNotEquals(expected.getSName(), actual.getSName(), "This should return true");
        Assertions.assertNotEquals(expected.getSPass(), actual.getSPass(), "This should return true");
    }
}