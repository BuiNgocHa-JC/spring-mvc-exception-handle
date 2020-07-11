package org.example.springcrud.controller;

import org.example.springcrud.exception.StudentNotFoundException;
import org.example.springcrud.model.Student;
import org.example.springcrud.model.StudentErrHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {
    private List<Student> theStudent;

    @PostConstruct
    public void loadData(){
        theStudent = new ArrayList<Student>();
        theStudent.add(new Student("john","Wick"));
        theStudent.add(new Student("john","cage"));
        theStudent.add(new Student("john","puss"));

    }
    @GetMapping("/student")
    public List<Student> getStudent(){

        return theStudent;
    }

    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable int id){
        if (id > theStudent.size() || id < 0){
            throw new StudentNotFoundException("Student id not found");
        }
        return theStudent.get(id);
    }

    // TO CATCH ID OUT OF SIZE EXCEPTION

    @ExceptionHandler
    public ResponseEntity<StudentErrHandler> handlerException(StudentNotFoundException err){
        StudentErrHandler error =  new StudentErrHandler();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(err.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    // TO CATCH STRING TYPE EXCEPTION

    @ExceptionHandler
    public ResponseEntity<StudentErrHandler> handlerException(Exception err){
        StudentErrHandler error =  new StudentErrHandler();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(err.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

    }
}
