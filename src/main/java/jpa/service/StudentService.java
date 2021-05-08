package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.entitymodels.StudentCourses;
import jpa.mainrunner.SMSRunner;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;
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
        String pass=null;
        // do a try catch finally to catch exceptions
        try {
            // begins transaction
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Find student by email");
            //sets email paramater for query
            q.setParameter("email", email);
            //if the query is empty
            if (((Student) q.getSingleResult())!=null) {
                //get query results
                s = (Student) q.getSingleResult();
                //commit transaction
                em.getTransaction().commit();
                //returns query results
                return s;

            }
        } catch(NoResultException | IllegalArgumentException | EntityNotFoundException | RollbackException ex){
            //log the error
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
            Query q = em.createNamedQuery("CoursesByStudent");
            q.setParameter("email", email);
            // puts query results in a list
            List<StudentCourses> sc = q.getResultList();
            // creates a string to check against the result list
            String checkResults = "StudentCourses{" +
                    "email=" + email +
                    ", course_id='" + cId + "'" +
                    "}";

            //creates new student courses
            StudentCourses sCourses = new StudentCourses();
            // sc list is empty add the student course
            if (sc.size()==0){
                // sets variables for search
                sCourses.setCourseID(cId);
                sCourses.seteMail(email);
                em.persist(sCourses);
            }
            else {
                // for loop to cycle through the list of results
                for (StudentCourses studentCourses : sc) {
                    // if true input the email and id into the StudentCourses table
                    if (!studentCourses.toString().equals(checkResults)) {

                        // sets variables for search
                        sCourses.seteMail(email);
                        sCourses.setCourseID(cId);
                        em.persist(sCourses);
                    }
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
