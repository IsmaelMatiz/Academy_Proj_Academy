package com.academy.AcademyApi.repository;

import com.academy.AcademyApi.model.StudentsTeachers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentsTeachersRepository extends JpaRepository<StudentsTeachers, Long> {

    StudentsTeachers findByEmail(String email);
}
