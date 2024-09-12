package com.academy.AcademyApi.model;

import com.academy.AcademyApi.model.contantsEntities.UserType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "STUDENTS_TEACHERS", schema = "ACADEMY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentsTeachers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "CHOSEN_CAREER")
    private int choosenCareer;
}
