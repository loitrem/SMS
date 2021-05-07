package jpa.service;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.mainrunner.SMSRunner;
import lombok.extern.log4j.Log4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;
@Log4j
public class CourseService implements CourseDAO {
    @Override
    public List<Course> getAllCourses() {
        // opens entity manager
        EntityManager em = SMSRunner.emf.createEntityManager();
        // begins transaction
        em.getTransaction().begin();
        // sets q to the named query find all students
        Query q = em.createNamedQuery("Find all courses");
        // puts results of query in list
        List<Course> c = q.getResultList();
        // commits transaction
        em.getTransaction().commit();
        //closes entity manager
        em.close();
        // returns student list
        return c;
    }

    @Override
    public Course getCourseById(int id) {
        // opens entity manager
        EntityManager em = SMSRunner.emf.createEntityManager();
        Course c = null;
        // do a try catch finally to catch exceptions
        try {
            // begins transaction
            em.getTransaction().begin();
            // sets q to the named query find all students
            Query q = em.createNamedQuery("Find course by id");
            // sets email parameter for search
            q.setParameter("id", id);

            c = (Course) q.getSingleResult();
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
        // returns student
        return c;
    }


}
