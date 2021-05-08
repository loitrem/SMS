package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.entitymodels.StudentCourses;
import jpa.mainrunner.SMSRunner;
import lombok.extern.log4j.Log4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;
import java.util.Objects;

@Log4j
public class StudentService implements StudentDAO {

    @Override
    public List<Student> getAllStudents() {
        // opens entity manager
        EntityManager em = SMSRunner.emf.createEntityManager();
        // begins transaction
        em.getTransaction().begin();
        // sets q to the named query find all students
        Query q = em.createNamedQuery("Find all students");
        // puts results of query in list
        List<Student> s = q.getResultList();
        // commits transaction
        em.getTransaction().commit();
        //closes entity manager
        em.close();
        // returns student list
        return s;

    }

    @Override
    public Student getStudentByEmail(String email) {
        // opens entity manager
        EntityManager em = SMSRunner.emf.createEntityManager();
        Student s = null;
        // do a try catch finally to catch exceptions
        try {
            // begins transaction
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Find student by email");
            q.setParameter("email", email);

            s = (Student) q.getSingleResult();
            //close everything
            em.getTransaction().commit();
            return s;
        } catch(IllegalArgumentException | EntityNotFoundException | RollbackException ex){
            // print stack trace and log the error
            ex.printStackTrace();
            log.error("commit issue or no records found!");
        } finally {
            //closes entity manager
            em.close();
        }
        // returns student
        return s;
    }

    @Override
    public boolean validateStudent(String email, String pass) {
        // opens entity manager
        EntityManager em = SMSRunner.emf.createEntityManager();

        // gets the student by email
        Student s = getStudentByEmail(email);

        if (s!=null){
            return s.getSEmail().equals(email);
        }
        else {
            return false;
        }
    }

    @Override
    public void registerStudentToCourse(String email, int cId) {
        // opens entity manager
        EntityManager em = SMSRunner.emf.createEntityManager();
        // do a try catch finally to catch exceptions
        try {
            // begins transaction
            em.getTransaction().begin();
            // sets q to the named query find all students
            Query q = em.createNamedQuery("Find all studentcourses");
            // puts query results in a list
            List<StudentCourses> sc = q.getResultList();
            // creates a string to check against the result list
            String checkResults = "StudentCourses{" +
                    "email=" + email +
                    ", course_id='" + cId + "'" +
                    "}";
            // sets a boolean to tell us if there was a match between supplied email and cId and what is in the database

            // for loop to cycle through the list of results
            for (StudentCourses studentCourses : sc) {
                // if true input the email and id into the StudentCourses table
                if (!studentCourses.toString().equals(checkResults)) {
                    //creates new student courses
                    StudentCourses sCourses = new StudentCourses();
                    // sets variables for search
                    sCourses.seteMail(email);
                    sCourses.setCourseID(cId);
                    em.persist(sCourses);
                }
            }

            //commit transaction
            em.getTransaction().commit();

        } catch (IllegalArgumentException | EntityNotFoundException | RollbackException ex) {
            // print stack trace and log the error
            ex.printStackTrace();
            log.error("commit issue or no records found!");
        } finally {
            //closes entity manager
            em.close();
        }
    }


    public List<StudentCourses> getStudentCourses(String email) {
        // opens entity manager
        EntityManager em = SMSRunner.emf.createEntityManager();
        List<StudentCourses> c = null;
        // do a try catch finally to catch exceptions
        try {
            // begins transaction
            em.getTransaction().begin();
            // sets q to the named query find all student courses
            Query q = em.createNamedQuery("CoursesByStudent");
            // sets email parameter for search
            q.setParameter("email", email);

            c = q.getResultList();
            //close everything
            em.getTransaction().commit();

        } catch(IllegalArgumentException | EntityNotFoundException | RollbackException ex){
            // print stack trace and log the error
            ex.printStackTrace();
            log.error("commit issue or no records found!");
        } finally {
            //closes entity manager
            em.close();
        }
        // returns course list
        return c;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
