package com.academy.AcademyApi.repository;

import com.academy.AcademyApi.model.ProgressTracker;
import com.academy.AcademyApi.model.StudentsTeachers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressTrackerRepository extends JpaRepository<ProgressTracker,Long> {
    ProgressTracker findByStudentId(Long studentId);
}
