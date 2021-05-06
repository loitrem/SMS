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
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(columnDefinition = "INT(11) UNSIGNED", name = "id")@NonNull
    Integer cId;
    @Column(columnDefinition = "VARCHAR(50)", name = "name")@NonNull
    String cName;
    @Column(columnDefinition = "VARCHAR(50)", name = "instructor")@NonNull
    String cInstructorName;
}
