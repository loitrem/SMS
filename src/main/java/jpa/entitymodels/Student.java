package jpa.entitymodels;

import javassist.util.proxy.ProxyFactory;
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
        @NamedQuery(name = "Find student by email", query = "FROM Student s WHERE s.sEmail = :email")
})
public class Student {
    // makes email primary key
    @Id
    // sets to varchar in database, renames, and not null
    @Column(columnDefinition = "VARCHAR(50)", name = "email", nullable = false)
    String sEmail;
    @Column(columnDefinition = "VARCHAR(50)", name = "name", nullable = false)
    String sName;
    @Column(columnDefinition = "VARCHAR(50)", name = "password", nullable = false)
    String sPass;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) @JoinColumn(name = "student_email") @ToString.Exclude
    List<StudentCourses> sCourses;

}
