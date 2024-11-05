package com.academy.AcademyApi.controller;

import com.academy.AcademyApi.model.Career;
import com.academy.AcademyApi.model.Lesson;
import com.academy.AcademyApi.model.Subject;
import com.academy.AcademyApi.repository.CareerRepository;
import com.academy.AcademyApi.repository.LessonRepository;
import com.academy.AcademyApi.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LessonController {

    @Autowired
    LessonRepository lessonRepository;

    @GetMapping("/Lessons")
    public ResponseEntity<?> getLessonsByCareerAndBimesterAndWeek(
            @RequestParam("careerId") Long careerId,
            @RequestParam("bimesterNum") Integer bimesterNum,
            @RequestParam("weekNum") Integer weekNum) {

        System.out.println("Recibi career: "+ careerId+
                            " recibi bimester: "+ bimesterNum+
                            " weekNum: "+weekNum);
        List<Lesson> lessons = lessonRepository.findByCareerIdAndBimesterNumAndWeekNum(careerId, bimesterNum, weekNum);
        if (lessons.isEmpty()) {
            return new ResponseEntity<>("No se han encontrado Lecciones", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }

    @PostMapping("/Lessons")
    public ResponseEntity<?> createLesson(@RequestBody Lesson lesson) {
        try{
            Lesson savedLesson = lessonRepository.save(lesson);
            return new ResponseEntity<>("Leccion posteada exitosamente con el Id |"+savedLesson.getLessonId(),
                    HttpStatus.OK);
        }catch (Exception e)
        {
            System.out.println("Error posteando una Leccion" +
                "\nmessage: "+e.getMessage()+
                "\nstack: ");
            for (var error : e.getStackTrace()) {
                System.out.println(error);
            }
            return new ResponseEntity<>("Error interno intenta mas tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/Lessons/{id}")
    public ResponseEntity<?> updateLesson(@PathVariable Long id, @RequestBody Lesson lessonDetails) {
        try {
            Optional<Lesson> existingLessonOpt = lessonRepository.findById(id);

            if (existingLessonOpt.isEmpty()) {
                return new ResponseEntity<>("No se encontró una Leccion con el ID: " + id, HttpStatus.NOT_FOUND);
            }

            Lesson existingLesson = existingLessonOpt.get();

            // Actualiza la información de la carrera
            existingLesson.setSubject(lessonDetails.getSubject());
            existingLesson.setCareer(lessonDetails.getCareer());
            existingLesson.setNumBimester(lessonDetails.getNumBimester());
            existingLesson.setNumWeek(lessonDetails.getNumWeek());
            existingLesson.setPosWeek(lessonDetails.getPosWeek());
            existingLesson.setLessonTitle(lessonDetails.getLessonTitle());
            existingLesson.setTeacherName(lessonDetails.getTeacherName());
            existingLesson.setContentDescrip(lessonDetails.getContentDescrip());
            existingLesson.setLinkToContent(lessonDetails.getLinkToContent());
            existingLesson.setContentType(lessonDetails.getContentType());

            lessonRepository.save(existingLesson);

            return new ResponseEntity<>("Leccion actualizada exitosamente", HttpStatus.OK);
        }catch (Exception e) {
            System.out.println("Error actualizando la Leccion" +
                    "\nmessage: " + e.getMessage() +
                    "\nstack: " + e.getStackTrace());
            return new ResponseEntity<>("Error interno, intenta más tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/Lessons/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
        try {
            Optional<Lesson> lesson = lessonRepository.findById(id);

            if (lesson.isEmpty()) {
                return new ResponseEntity<>("No se encontró una leccion con el ID: " + id, HttpStatus.NOT_FOUND);
            }

            lessonRepository.deleteById(id);

            return new ResponseEntity<>("Leccion eliminada exitosamente", HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Error eliminando la leccion" +
                    "\nmessage: " + e.getMessage() +
                    "\nstack: " + e.getStackTrace());
            return new ResponseEntity<>("Error interno, intenta más tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
