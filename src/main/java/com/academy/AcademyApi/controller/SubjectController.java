package com.academy.AcademyApi.controller;

import com.academy.AcademyApi.model.Subject;
import com.academy.AcademyApi.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;

    @GetMapping("/Subjects")
    public ResponseEntity<List<Subject>> getAllSubjects(){
        try{
            List<Subject> subjects = new ArrayList<>();
            Iterable<Subject> result = subjectRepository.findAll();

            result.forEach(item -> {
                subjects.add(new Subject(item.getSubjectId(),item.getName()));
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
}
