package com.academy.AcademyApi.repository;

import com.academy.AcademyApi.model.DirectorAcademico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DirectorRepository extends JpaRepository<DirectorAcademico, Long> {
    Optional<DirectorAcademico> findByEmail(String email);
}
