/*
 * Filename: SMSRunner.java
* Author: Stefanski
* 02/25/2020 
 */
package jpa.mainrunner;

import static java.lang.System.out;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.entitymodels.StudentCourses;
import jpa.service.CourseService;
import jpa.service.StudentCourseService;
import jpa.service.StudentService;
import jpa.util.SMSUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

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
		int run = 99;
		while (run != 0) {
			// Login or quit
			switch (menu1()) {
				case 1:
					if (studentLogin()) {
						registerMenu();
					}
					break;
				case 2:
					studentService.addStudent();
					break;
				case 3:
					System.out.println("\n********" +
							"\nGoodbye!" +
							"\n********");
					run = 0;
					break;

				default:

					break;

			}
		}
	}


	private int menu1() {
		System.out.println("\nPlease select an option ");
		System.out.println("________________________________");
		sb.append("\n1. Student Login\n2. Register as a new Student\n3. Quit Application\nPlease Enter Selection: ");
		System.out.print(sb.toString());
		sb.delete(0, sb.length());
		try {
			return sin.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("\n**************************************************************" +
					"\nInput error. Please choose a number from the menu to continue." +
					"\n**************************************************************");
			sin.nextLine();
			run();

		}
		return 0;
	}

	private boolean studentLogin() {
		boolean retValue = false;
		out.print("Enter your email address: ");
		String email = sin.next();
		out.print("Enter your password: ");
		String password = sin.next().trim();

		Student students = studentService.getStudentByEmail(email);
		if (students != null) {
			currentStudent = students;
		}

		if (currentStudent != null && currentStudent.getSPass().equals(password)) {
			List<StudentCourses> courses = studentService.getStudentCourses(email);
			System.out.println("\nMyClasses");
			System.out.println("___________________________________________________________");
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
			System.out.println("\n*******************************************************" +
					"\nUser Validation failed. Email or Password is incorrect." +
					"\n*******************************************************");
		}
		return retValue;
	}

	private void registerMenu() {
		boolean b = true;
		while (studentService.loggedIn(b)) {
			sb.append("\n1. Register a class\n2. Withdraw from class\n3. Logout\nPlease Enter Selection: ");
			System.out.print(sb.toString());
			sb.delete(0, sb.length());
			try {
			switch (sin.nextInt()) {
				case 1:
					List<Course> allCourses = courseService.getAllCourses();

					System.out.printf("%-5s %-35s %-25s\n", "ID", "Course", "Instructor");
					for (Course course : allCourses) {
						System.out.printf("%-5s %-35s %-25s\n", course.getCId(), course.getCName(), course.getCInstructorName());

					}

					System.out.println("\nEnter Course Number: ");
					int number = sin.nextInt();
					Course newCourse = courseService.getCourseById(number);

					if (newCourse != null) {
						studentService.registerStudentToCourse(currentStudent.getSEmail(), newCourse.getCId());
						Student temp = studentService.getStudentByEmail(currentStudent.getSEmail());

						StudentCourseService scService = new StudentCourseService();
						List<StudentCourses> sCourses = scService.getAllStudentCourses(temp.getSEmail());

						Course c = new Course();
						CourseService cs = new CourseService();
						System.out.println("MyClasses");
						System.out.printf("%-5s %-35s %-25s\n", "ID", "Course Name", "Instructor Name");
						for (StudentCourses course : sCourses) {
							c.setCId(course.getCourseID());
							Course c1 = cs.getCourseById(c.getCId());

							System.out.printf("%-5s %-35s %-25s\n", c1.getCId(), c1.getCName(), c1.getCInstructorName());
						}
					}


					break;

				case 2:

					StudentCourseService scService = new StudentCourseService();
					List<StudentCourses> sCourses = scService.getAllStudentCourses(currentStudent.getSEmail());

					Course c = new Course();
					CourseService cs = new CourseService();
					System.out.println("\nMyClasses");
					System.out.printf("%-5s %-35s %-25s\n", "ID", "Course Name", "Instructor Name");
					for (StudentCourses course : sCourses) {
						c.setCId(course.getCourseID());
						Course c1 = cs.getCourseById(c.getCId());

						System.out.printf("%-5s %-35s %-25s\n", c1.getCId(), c1.getCName(), c1.getCInstructorName());
					}
					System.out.println("\nEnter Course Number to remove: ");
					int number2 = sin.nextInt();
					Course newCourse2 = courseService.getCourseById(number2);
					studentService.removeStudentFromCourse(currentStudent.getSEmail(), newCourse2.getCId());
					break;

				case 3:
					b = false;
					break;


				default:
			}
			} catch (InputMismatchException e) {
				System.out.println("**************************************************************" +
						"\nInput error. Please choose a number from the menu to continue." +
						"\n**************************************************************");
				sin.nextLine();
				registerMenu();

			}
		}
	}
}


