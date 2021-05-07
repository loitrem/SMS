package jpa.entitymodels;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;
import java.util.*;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Log4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@NamedQueries({
        // created named queries
        @NamedQuery(name = "Find all students", query = "SELECT s FROM Student s"),
        @NamedQuery(name = "Find student by email", query = "SELECT s FROM Student s WHERE s.email = :email"),
        @NamedQuery(name = "Get password", query = "SELECT s.password FROM Student s WHERE s.email = :email"),
        @NamedQuery(name = "Register student to course", query = "INSERT INTO StudentCourses sc VALUES :cId WHERE sc.student_email = :email")
})
public class Student {
    // makes email primary key
    @Id
    // sets to varchar in database, renames, and not null
    @Column(columnDefinition = "VARCHAR(50)", name = "email")@NonNull
    String sEmail;
    @Column(columnDefinition = "VARCHAR(50)", name = "name")@NonNull
    String sName;
    @Column(columnDefinition = "VARCHAR(50)", name = "password")@NonNull
    String sPass;
    List<Course> sCourses;

}
