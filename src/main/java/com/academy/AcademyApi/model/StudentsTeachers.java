package com.academy.AcademyApi.model;

import com.academy.AcademyApi.model.contantsEntities.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "STUDENTS_TEACHERS", schema = "ACADEMY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentsTeachers {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "EMAIL")
    private String email;

    @Lob
    @Column(name = "PROFILE_PIC")
    private byte[] profilePic;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_TYPE")
    private UserType userType;

    // Relación One-to-Many con Career
    // Relación Many-to-One con Career
    @ManyToOne
    @JoinColumn(name = "CAREER_ID")  // Llave foránea en StudentsTeachers que referencia a Career
    private Career choosenCareer;
}
