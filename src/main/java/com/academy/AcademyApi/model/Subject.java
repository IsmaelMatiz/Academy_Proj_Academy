package com.academy.AcademyApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    private Long subjectId;

    @Column(name = "NAME")
    private String name;

    // Relación Many-to-Many con Career (no es necesario especificar @JoinTable aquí)
    @ManyToMany(mappedBy = "subjects")
    @JsonIgnore
    private Set<Career> careers = new HashSet<>();
}
