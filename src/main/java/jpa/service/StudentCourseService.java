package jpa.service;

import jpa.entitymodels.Course;
import jpa.entitymodels.StudentCourses;
import jpa.mainrunner.SMSRunner;
import lombok.extern.log4j.Log4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;
@Log4j
public class StudentCourseService {

    public List<StudentCourses> getAllStudentCourses(String email) {
        // opens entity manager
        EntityManager em = SMSRunner.emf.createEntityManager();
        List<StudentCourses> sc = null;
        // do a try catch finally to catch exceptions
        try {
            // begins transaction
            em.getTransaction().begin();
            // sets q to the named query find all student courses
            Query q = em.createQuery("SELECT sc FROM StudentCourses sc WHERE sc.eMail = :email");
            // sets email parameter for search
            q.setParameter("email", email);

            sc = q.getResultList();

            //close everything
            em.getTransaction().commit();

            return sc;

        } catch(IllegalArgumentException | EntityNotFoundException | RollbackException ex){
            // print stack trace and log the error
            ex.printStackTrace();
            log.error("commit issue or no records found!");
        } finally {
            //closes entity manager
            em.close();
        }
        // returns course list
        return sc;
    }
}
