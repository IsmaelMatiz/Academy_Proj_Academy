package com.academy.AcademyApi.controller;

import com.academy.AcademyApi.model.ProgressTracker;
import com.academy.AcademyApi.model.StudentProgressInfo;
import com.academy.AcademyApi.model.StudentsTeachers;
import com.academy.AcademyApi.repository.ProgressTrackerRepository;
import com.academy.AcademyApi.repository.StudentsTeachersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentCareerInfoController {
    @Autowired
    StudentsTeachersRepository studentsTeachersRepository;
    @Autowired
    ProgressTrackerRepository progressTrackerRepository;

    @GetMapping("/Student/Career/{email}")
    public ResponseEntity<?> getStudentInfo(@PathVariable("email") String email) {
        // <editor-fold defaultstate="collapsed" desc="Get Student profile Info">
        System.out.println("email es: "+email);
        if (email == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se puede obtener el correo: " + email);
        }

        StudentsTeachers studentsProfile = studentsTeachersRepository.findByEmail(email);

        if (studentsProfile == null) {
            // Si no se encuentra el perfil del estudiante, devuelve 404 con un mensaje personalizado
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un estudiante con el email: " + email);
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Get student progress">
        ProgressTracker studentProgress = progressTrackerRepository.findByStudentId(studentsProfile.getId());

        if (studentProgress == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró progreso para el estudiante con email: " + email);
        }
        // </editor-fold>

        StudentProgressInfo studentInfo = new StudentProgressInfo(
                studentsProfile,
                studentProgress
        );

        return new ResponseEntity<>(studentInfo, HttpStatus.OK);
    }
}
