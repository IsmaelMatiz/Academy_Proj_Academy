package com.academy.AcademyApi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SUBJECTS", schema = "ACADEMY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBJECT_ID")
    private long subjectId;

    @Column(name = "NAME")
    private String name;

}
