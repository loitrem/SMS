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
public class Student {

    @Id
    @Column(columnDefinition = "VARCHAR(50)", name = "email")@NonNull
    String sEmail;
    @Column(columnDefinition = "VARCHAR(50)", name = "name")@NonNull
    String sName;
    @Column(columnDefinition = "VARCHAR(50)", name = "password")@NonNull
    String sPass;
    List<Course> sCourses;

}
