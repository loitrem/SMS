package jpa.service;

import jpa.entitymodels.Student;
import jpa.service.StudentService;
import jpa.service.StudentService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.*;


import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.function.BooleanSupplier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)// per class will instantiate once per class vs once for every method
public class StudentServiceTest {

    @Test
    @DisplayName("validateStudent method to check for problems")
    public void testValidateStudent(){
        String EXPECTED_EMAIL = "aiannitti7@is.gd";
        String EXPECTED_PASSWORD = "TWP4hf5j";

        StudentService testStudentService = new StudentService();

        Assertions.assertTrue(testStudentService.validateStudent(EXPECTED_EMAIL,EXPECTED_PASSWORD),"This should return true");
    }

    @ParameterizedTest
    @CsvSource({
            "aiannitti7@is.gd,Alexandra Iannitti,TWP4hf5j",
            "qllorens2@howstuffworks.com,Quillan Llorens,W6rJuxd"
    })
    public void testGetStudentByEmail(ArgumentsAccessor arg) throws ArgumentAccessException, SQLException {
        Student exptected = new Student();
        exptected.setSEmail(arg.getString(0));
        exptected.setSName(arg.getString(1));
        exptected.setSPass(arg.getString(2));

        StudentService testStudentService = new StudentService();
        Student actual = testStudentService.getStudentByEmail(arg.getString(0));

        Assertions.assertEquals(exptected.getSEmail(), actual.getSEmail());
        Assertions.assertEquals(exptected.getSName(), actual.getSName());
        Assertions.assertEquals(exptected.getSPass(), actual.getSPass());
    }
}
