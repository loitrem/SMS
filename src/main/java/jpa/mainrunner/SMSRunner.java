/*
 * Filename: SMSRunner.java
* Author: Stefanski
* 02/25/2020 
 */
package jpa.mainrunner;

import static java.lang.System.out;

import java.util.List;
import java.util.Scanner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.entitymodels.StudentCourses;
import jpa.service.CourseService;
import jpa.service.StudentCourseService;
import jpa.service.StudentService;
import jpa.util.SMSUtil;

import javax.persistence.EntityManagerFactory;

/**1
 * 
 * @author Harry
 *
 */
public class SMSRunner {

	public static EntityManagerFactory emf = SMSUtil.getEntityManagerFactory();


	private Scanner sin;
	private StringBuilder sb;

	private CourseService courseService;
	private StudentService studentService;
	private Student currentStudent;

	public SMSRunner() {
		sin = new Scanner(System.in);
		sb = new StringBuilder();
		courseService = new CourseService();
		studentService = new StudentService();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SMSRunner sms = new SMSRunner();
		sms.run();
	}

	private void run() {
		// Login or quit
		switch (menu1()) {
		case 1:
			if (studentLogin()) {
				registerMenu();
			}
			break;
		case 2:
			out.println("Goodbye!");
			break;

		default:

		}
	}

	private int menu1() {
		sb.append("\n1.Student Login\n2. Quit Application\nPlease Enter Selection: ");
		out.print(sb.toString());
		sb.delete(0, sb.length());

		return sin.nextInt();
	}

	private boolean studentLogin() {
		boolean retValue = false;
		out.print("Enter your email address: ");
		String email = sin.next();
		out.print("Enter your password: ");
		String password = sin.next();

		Student students = studentService.getStudentByEmail(email);
		if (students != null) {
			currentStudent = students;
		}

		if (currentStudent != null && currentStudent.getSPass().equals(password)) {
			List<StudentCourses> courses = studentService.getStudentCourses(email);
			out.println("MyClasses");
			Course c = new Course();
			CourseService cs = new CourseService();
			System.out.printf("%-5s %-35s %-25s\n", "ID", "Course Name", "Instructor Name");
			for (StudentCourses course : courses) {
				c.setCId(course.getCourseID());
				Course c1 = cs.getCourseById(c.getCId());

				System.out.printf("%-5s %-35s %-25s\n", c1.getCId(), c1.getCName(), c1.getCInstructorName());

			}
			retValue = true;
		} else {
			System.out.println("User Validation failed. GoodBye!");
		}
		return retValue;
	}

	private void registerMenu() {
		sb.append("\n1.Register a class\n2. Logout\nPlease Enter Selection: ");
		System.out.print(sb.toString());
		sb.delete(0, sb.length());

		switch (sin.nextInt()) {
		case 1:
			List<Course> allCourses = courseService.getAllCourses();
			//List<Course> studentCourses = studentService.getStudentCourses(currentStudent.getSEmail());
			//allCourses.removeAll(studentCourses);
			System.out.printf("%-5s %-35s %-25s\n", "ID", "Course", "Instructor");
			for (Course course : allCourses) {
				System.out.printf("%-5s %-35s %-25s\n", course.getCId(), course.getCName(), course.getCInstructorName());

			}
			System.out.println();
			System.out.print("Enter Course Number: ");
			int number = sin.nextInt();
			Course newCourse = courseService.getCourseById(number);

			if (newCourse != null) {
				studentService.registerStudentToCourse(currentStudent.getSEmail(), newCourse.getCId());
				Student temp = studentService.getStudentByEmail(currentStudent.getSEmail());

				StudentCourseService scService = new StudentCourseService();
				List<StudentCourses> sCourses = scService.getAllStudentCourses(temp.getSEmail());

				Course c = new Course();
				CourseService cs = new CourseService();
				System.out.printf("%-5s %-35s %-25s\n", "ID", "Course Name", "Instructor Name");
				System.out.println("MyClasses");
				for (StudentCourses course : sCourses) {
					c.setCId(course.getCourseID());
					Course c1 = cs.getCourseById(c.getCId());

					System.out.printf("%-5s %-35s %-25s\n", c1.getCId(), c1.getCName(), c1.getCInstructorName());

				}
			}
			break;
		case 2:
		default:
			System.out.println("Goodbye!");
		}
	}

}
