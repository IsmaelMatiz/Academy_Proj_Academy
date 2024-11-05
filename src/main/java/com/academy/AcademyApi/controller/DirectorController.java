package com.academy.AcademyApi.controller;

import com.academy.AcademyApi.model.DirectorAcademico;
import com.academy.AcademyApi.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DirectorController {
    @Autowired
    DirectorRepository directorRepository;

    @GetMapping("/Directores")
    public ResponseEntity<?> getAllDirectores() {
        List<DirectorAcademico> directores = directorRepository.findAll();
        if (directores.isEmpty()) {
            return new ResponseEntity<>("No se encontraron directores académicos.",HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(directores,HttpStatus.OK);
    }

    @GetMapping("/Directores/{email}")
    public ResponseEntity<?> getDirectorById(@PathVariable String email) {
        Optional<DirectorAcademico> director = directorRepository.findByEmail(email);
        if (director.isPresent()) {
            return new ResponseEntity<>(director.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Director académico no encontrado con email: "+email, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/Directores")
    public ResponseEntity<?> createDirector(@RequestBody DirectorAcademico newDirector) {
        try {
            DirectorAcademico savedDirector = directorRepository.save(newDirector);
            return new ResponseEntity<>("Director Creado Exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el director académico: " + e.getMessage()
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/Directores/{id}")
    public ResponseEntity<?> updateDirector(@PathVariable Long id, @RequestBody DirectorAcademico updatedDirector) {
        return directorRepository.findById(id)
                .map(director -> {
                    try {
                        director.setFullName(updatedDirector.getFullName());
                        director.setEmail(updatedDirector.getEmail());
                        director.setProfilePic(updatedDirector.getProfilePic());
                        DirectorAcademico savedDirector = directorRepository.save(director);
                        return new ResponseEntity<>("Director actualizado Exitosamente",HttpStatus.OK);
                    } catch (Exception e) {
                        return new ResponseEntity<>("Error al actualizar el director académico: "
                                + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                })
                .orElseGet(() -> new ResponseEntity<>("Director académico no encontrado con ID: " + id,
                        HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/Directores/{id}")
    public ResponseEntity<?> deleteDirector(@PathVariable Long id) {
        if (directorRepository.existsById(id)) {
            try {
                directorRepository.deleteById(id);
                return new ResponseEntity<>("Director académico eliminado exitosamente.", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error al eliminar el director académico: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Director académico no encontrado con ID: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
