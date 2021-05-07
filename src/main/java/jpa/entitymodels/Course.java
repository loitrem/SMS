package jpa.entitymodels;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Log4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@NamedQueries({
        // created named queries
        @NamedQuery(name = "Find course by id", query = "SELECT c FROM Course c WHERE c.id = :id"),
        @NamedQuery( name="Find all courses", query="Select c from Courses c")
})
public class Course {
    // variables / fields
    //make id primary key
    @Id
    // make id auto generate and assign it as int and not null
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(columnDefinition = "INT(11) UNSIGNED", name = "id")@NonNull
    Integer cId;
    //assign as varchar in database, name it, and not null
    @Column(columnDefinition = "VARCHAR(50)", name = "name")@NonNull
    String cName;
    @Column(columnDefinition = "VARCHAR(50)", name = "instructor")@NonNull
    String cInstructorName;
}
