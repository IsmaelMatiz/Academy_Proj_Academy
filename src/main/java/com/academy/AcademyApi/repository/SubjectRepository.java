package com.academy.AcademyApi.repository;

import com.academy.AcademyApi.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
