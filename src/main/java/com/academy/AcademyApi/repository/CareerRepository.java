package com.academy.AcademyApi.repository;

import com.academy.AcademyApi.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career,Long> {
}
