package com.academy.AcademyApi.controller;

import com.academy.AcademyApi.model.ProgressTracker;
import com.academy.AcademyApi.model.StudentsTeachers;
import com.academy.AcademyApi.model.contantsEntities.Status;
import com.academy.AcademyApi.repository.ProgressTrackerRepository;
import com.academy.AcademyApi.repository.StudentsTeachersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StudentCareerInfoController {
    @Autowired
    StudentsTeachersRepository studentsTeachersRepository;
    @Autowired
    ProgressTrackerRepository progressTrackerRepository;

    @GetMapping("/Student/{email}")
    public ResponseEntity<?> getStudentInfo(@PathVariable("email") String email) {
        // <editor-fold defaultstate="collapsed" desc="Get Student profile Info">
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

        return new ResponseEntity<>(studentProgress, HttpStatus.OK);
    }

    @PostMapping("/Student")
    public ResponseEntity<?> postStudentInfo(@RequestBody StudentsTeachers studentInfo){
        try {
            studentsTeachersRepository.save(studentInfo);
            progressTrackerRepository.save(new ProgressTracker(
                            studentInfo,
                            Status.ACTIVE,
                            1,
                            1,
                            0
                    )
            );

            return  ResponseEntity.status(HttpStatus.OK).body("Estudiante Registrado exitosamente");
        }
        catch (DataIntegrityViolationException e) {
            // Captura problemas de integridad, como violaciones de clave única
            System.out.println("Error de integridad de datos: " + e.getMessage());
            System.out.println("Error de integridad de datos: " + e.getStackTrace());
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error de integridad de datos: \n" +
                    ""+e.getMessage());
        } catch (JpaSystemException e) {
            // Captura errores relacionados con el sistema de JPA
            System.out.println("Error del sistema JPA: " + e.getMessage());
            System.out.println("Error del sistema JPA: " + e.getStackTrace());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en JPA al registrar entidad: \n" +
                    ""+e.getMessage());
        } catch (Exception e) {
            // Maneja cualquier otro tipo de excepción
            System.out.println("Error al guardar el registro: " + e.getMessage());
            System.out.println("Error al guardar el registro: " + e.getStackTrace());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar entidad: \n" +
                    ""+e.getMessage());
        }
    }

    @PutMapping("/Student/{id}")
    public ResponseEntity<?> updateStudentInfo(@PathVariable("id") Long id, @RequestBody StudentsTeachers updatedStudentInfo) {
        try {
            // Verifica si el estudiante existe
            StudentsTeachers existingStudent = studentsTeachersRepository.findById(id).orElse(null);

            if (existingStudent == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró un estudiante con el ID: " + id);
            }

            // Actualiza la información del estudiante
            existingStudent.setFullName(updatedStudentInfo.getFullName());
            existingStudent.setEmail(updatedStudentInfo.getEmail());
            existingStudent.setProfilePic(updatedStudentInfo.getProfilePic());
            existingStudent.setUserType(updatedStudentInfo.getUserType());
            existingStudent.setChoosenCareer(updatedStudentInfo.getChoosenCareer());

            studentsTeachersRepository.save(existingStudent);

            return ResponseEntity.status(HttpStatus.OK).body("Estudiante actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el estudiante: " + e.getMessage());
        }
    }

    @DeleteMapping("/Student/{id}")
    public ResponseEntity<?> deleteStudentInfo(@PathVariable("id") Long id) {
        try {
            // Verifica si el estudiante existe
            StudentsTeachers student = studentsTeachersRepository.findById(id).orElse(null);

            if (student == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró un estudiante con el ID: " + id);
            }

            // Elimina el progreso del estudiante asociado
            ProgressTracker studentProgress = progressTrackerRepository.findByStudentId(id);

            if (studentProgress != null) {
                progressTrackerRepository.delete(studentProgress);
            }

            // Elimina el perfil del estudiante
            studentsTeachersRepository.delete(student);

            return ResponseEntity.status(HttpStatus.OK).body("Estudiante y su progreso eliminados exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el estudiante: " + e.getMessage());
        }
    }
}
