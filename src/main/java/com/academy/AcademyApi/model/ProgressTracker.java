package com.academy.AcademyApi.model;

import com.academy.AcademyApi.model.contantsEntities.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "STUDENT_PROGRESS_TRACKER", schema = "ACADEMY")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProgressTracker {

    @Id
    @GeneratedValue
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

    public ProgressTracker(StudentsTeachers student, Status status, int currentBimester, int currentWeek, int currentClass) {
        this.student = student;
        this.status = status;
        this.currentBimester = currentBimester;
        this.currentWeek = currentWeek;
        this.currentClass = currentClass;
    }
}


