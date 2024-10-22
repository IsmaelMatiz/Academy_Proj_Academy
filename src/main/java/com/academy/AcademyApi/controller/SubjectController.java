package com.academy.AcademyApi.controller;

import com.academy.AcademyApi.model.Career;
import com.academy.AcademyApi.model.Subject;
import com.academy.AcademyApi.repository.CareerRepository;
import com.academy.AcademyApi.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SubjectController {
    @Autowired
    CareerRepository careerRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @GetMapping("/Subjects")
    public ResponseEntity<List<Subject>> getAllSubjects(){
        try{
            List<Subject> subjects = new ArrayList<>();
            Iterable<Subject> result = subjectRepository.findAll();

            result.forEach(item -> {
                subjects.add(new Subject(item.getSubjectId(),item.getName(),item.getCareers()));
            });

            if(subjects.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(subjects, HttpStatus.OK);

        }catch (Exception e)
        {
            System.out.println("Algo salio mal: "+e.getMessage()+
                    "\n"+e.getStackTrace());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Subjects/{subjectId}")
    public ResponseEntity<?> getSubjectById(@PathVariable("subjectId") Long subjectId) {
        try {
            Optional<Subject> subject = subjectRepository.findById(subjectId);

            if (subject.isEmpty()) {
                return new ResponseEntity<>("No se encontró una asignatura con el ID: " + subjectId, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(subject.get(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error obteniendo la asignatura:" +
                    "\nmessage: " + e.getMessage() +
                    "\nstack: " + e.getStackTrace());
            return new ResponseEntity<>("Error interno, intenta más tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/Subjects")
    public ResponseEntity<?> createSubject(@RequestBody Subject subject) {
        try {
            // Para cada carrera en la asignatura, necesitamos asegurarnos de que se actualice la lista de asignaturas en la carrera
            for (Career career : subject.getCareers()) {
                // Buscar la carrera existente por ID
                Career existingCareer = careerRepository.findById(career.getCareerId())
                        .orElseThrow(() -> new RuntimeException("Career not found"));

                // Agregar la asignatura a la lista de asignaturas de la carrera
                existingCareer.getSubjects().add(subject);
            }

            subjectRepository.save(subject);
            return new ResponseEntity<>("Asignatura creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error creando la asignatura" +
                    "\nmessage: " + e.getMessage() +
                    "\nstack: " + e.getStackTrace());
            return new ResponseEntity<>("Error interno, intenta más tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/Subjects/{subjectId}")
    public ResponseEntity<?> updateSubject(@PathVariable("subjectId") Long subjectId, @RequestBody Subject updatedSubject) {
        try {
            Optional<Subject> existingSubjectOpt = subjectRepository.findById(subjectId);

            if (existingSubjectOpt.isEmpty()) {
                return new ResponseEntity<>("No se encontró una asignatura con el ID: " + subjectId, HttpStatus.NOT_FOUND);
            }

            Subject existingSubject = existingSubjectOpt.get();
            // Actualiza los campos
            existingSubject.setName(updatedSubject.getName());
            existingSubject.setCareers(updatedSubject.getCareers());

            subjectRepository.save(existingSubject);

            return new ResponseEntity<>("Asignatura actualizada exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error actualizando la asignatura" +
                    "\nmessage: " + e.getMessage() +
                    "\nstack: " + e.getStackTrace());
            return new ResponseEntity<>("Error interno, intenta más tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/Subjects/{subjectId}")
    public ResponseEntity<?> deleteSubject(@PathVariable("subjectId") Long subjectId) {
        try {
            Optional<Subject> subject = subjectRepository.findById(subjectId);

            if (subject.isEmpty()) {
                return new ResponseEntity<>("No se encontró una asignatura con el ID: " + subjectId, HttpStatus.NOT_FOUND);
            }

            subjectRepository.deleteById(subjectId);

            return new ResponseEntity<>("Asignatura eliminada exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error eliminando la asignatura" +
                    "\nmessage: " + e.getMessage() +
                    "\nstack: " + e.getStackTrace());
            return new ResponseEntity<>("Error interno, intenta más tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
