package com.academy.AcademyApi.model;

import com.academy.AcademyApi.model.contantsEntities.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "STUDENT_PROGRESS_TRACKER", schema = "ACADEMY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProgressTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRACKER_ID")
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
    private StudentsTeachers student;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Column(name = "CURRENT_BIMESTER")
    private int currentBimester;

    @Column(name = "CURRENT_WEEK")
    private int currentWeek;

    @Column(name = "CURRENT_CLASS")
    private int currentClass;
}


