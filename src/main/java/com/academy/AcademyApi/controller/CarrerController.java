package com.academy.AcademyApi.controller;

import com.academy.AcademyApi.model.Career;
import com.academy.AcademyApi.repository.CareerRepository;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarrerController {
    @Autowired
    CareerRepository careerRepository;

    @GetMapping("/Careers")
    public ResponseEntity<?> getCareers() {
        try{
            List<Career> careers = careerRepository.findAll();

            if (careers.isEmpty()){
                return new ResponseEntity<>("No hay Carreras registradas", HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(careers, HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Error obteniendo la lista de carreras:" +
                    "\nmessage: "+e.getMessage()+
                    "\nstack: "+e.getStackTrace());

            return new ResponseEntity<>("Error interno intenta mas tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Careers/{carrerId}")
    public ResponseEntity<?> getCareerById(@PathVariable("carrerId") Long careerId) {
        try{
            Optional<Career> career = careerRepository.findById(careerId);

            if (career.isEmpty()){
                return new ResponseEntity<>("No se ha encontrado carrera", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(career.get(), HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Error obteniendo la lista de carreras:" +
                    "\nmessage: "+e.getMessage()+
                    "\nstack: "+e.getStackTrace());

            return new ResponseEntity<>("Error interno intenta mas tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/Careers")
    public  ResponseEntity<?> addACareer(@RequestBody Career career){
        try {
            careerRepository.save(career);
            return new ResponseEntity<>("Carrera posteada exitosamente", HttpStatus.OK);
        }catch (Exception e)
        {
            System.out.println("Error posteando una carrera" +
                "\nmessage: "+e.getMessage()+
                "\nstack: "+e.getStackTrace());
            return new ResponseEntity<>("Error interno intenta mas tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/Careers/{careerId}")
    public ResponseEntity<?> updateCareer(@PathVariable("careerId") Long careerId, @RequestBody Career updatedCareer) {
        try {
            // Verifica si la carrera existe
            Optional<Career> existingCareerOpt = careerRepository.findById(careerId);

            if (existingCareerOpt.isEmpty()) {
                return new ResponseEntity<>("No se encontró una carrera con el ID: " + careerId, HttpStatus.NOT_FOUND);
            }

            Career existingCareer = existingCareerOpt.get();

            // Actualiza la información de la carrera
            existingCareer.setCareerName(updatedCareer.getCareerName());
            existingCareer.setNumBimesters(updatedCareer.getNumBimesters());
            existingCareer.setStudents(updatedCareer.getStudents());
            existingCareer.setSubjects(updatedCareer.getSubjects());

            careerRepository.save(existingCareer);

            return new ResponseEntity<>("Carrera actualizada exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error actualizando la carrera" +
                    "\nmessage: " + e.getMessage() +
                    "\nstack: " + e.getStackTrace());
            return new ResponseEntity<>("Error interno, intenta más tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/Careers/{careerId}")
    public ResponseEntity<?> deleteCareer(@PathVariable("careerId") Long careerId) {
        try {
            // Verifica si la carrera existe
            Optional<Career> career = careerRepository.findById(careerId);

            if (career.isEmpty()) {
                return new ResponseEntity<>("No se encontró una carrera con el ID: " + careerId, HttpStatus.NOT_FOUND);
            }

            // Elimina la carrera
            careerRepository.deleteById(careerId);

            return new ResponseEntity<>("Carrera eliminada exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error eliminando la carrera" +
                    "\nmessage: " + e.getMessage() +
                    "\nstack: " + e.getStackTrace());
            return new ResponseEntity<>("Error interno, intenta más tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
