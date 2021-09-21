package jpa.kea.demo.controller;

import jpa.kea.demo.model.Student;
import jpa.kea.demo.repository.StudentRep;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// RequestMapping af "/students", så man ikke behøver at skrive det i de andre mappings.
@RequestMapping("/students")
@RestController
public class StudentController {

    private StudentRep studentRep;

    //Constructor injection istedet for field injection (Autowired)
    public StudentController(StudentRep studentRep) {
        this.studentRep = studentRep;
    }

    //HTTP GET (/students)
    @GetMapping("")
    public ResponseEntity<List<Student>> findAll(){
        List<Student> allStudents = new ArrayList<>();
        studentRep.findAll().forEach(allStudents::add);
        //ResponseEntity builder - først status OK/200 - så til sidst body.
        return ResponseEntity.status(HttpStatus.OK).body(allStudents);
    }

    //HTTP GET (/students/{id}) - findById
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Student>> specificStudent(@PathVariable Long id){
        Optional<Student> specificStudent = studentRep.findById(id);
        if (specificStudent.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(specificStudent);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(specificStudent);
        }
    }

    //HTTP Post (/students) - create
    @CrossOrigin(origins = "*", exposedHeaders = "Location")
    @PostMapping(value = "")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        //Tjek om den allerede findes
        if(student.getId()!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        //opret ny student i JPA
        Student newStudent = studentRep.save(student);
        //location header: / students/{id}
        //"location", "students/" + newStudent.getId()
        //HttpStatus.CREATED 201
        String location = "/students/" + newStudent.getId();
        return ResponseEntity.status(HttpStatus.CREATED).
                header("Location", location).
                body(newStudent);
    }

    //HTTP Put (/students/{id}) - update
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Student student) {
        //Findes den studerende i systemet?
        Optional<Student> optionalStudent = studentRep.findById(id);
        if (optionalStudent.isPresent()) {
            if (id.equals(student.getId())) {
                studentRep.save(student);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //HTTP Delete (/students/{id}) - delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        Optional<Student> optinalStudent = studentRep.findById(id);
        if(optinalStudent.isPresent()){
            studentRep.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
