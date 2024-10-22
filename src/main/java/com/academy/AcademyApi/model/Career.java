package com.academy.AcademyApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CAREERS", schema = "ACADEMY")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Career {
    @Id
    @GeneratedValue
    @Column(name = "CAREER_ID")
    private Long careerId;

    @Column(name = "CAREER_NAME", columnDefinition = "VARCHAR(180)")
    private String careerName;

    @Column(name = "NUM_BIMESTERS", columnDefinition = "NUMBER(2)")
    private Integer numBimesters;

    // Relación One-to-Many con StudentsTeachers
    @JsonIgnore
    @OneToMany(mappedBy = "choosenCareer", cascade = CascadeType.PERSIST)
    private Set<StudentsTeachers> students = new HashSet<>();

    // Relación Many-to-Many con Subject
    @ManyToMany
    @JoinTable(
            name = "CAREERS_SUBJECTS", // Nombre de la tabla de unión
            schema = "ACADEMY",
            joinColumns = @JoinColumn(name = "CAREER_ID"), // Columna que referencia a Career
            inverseJoinColumns = @JoinColumn(name = "SUBJECT_ID") // Columna que referencia a Subject
    )
    private Set<Subject> subjects = new HashSet<>();
}
