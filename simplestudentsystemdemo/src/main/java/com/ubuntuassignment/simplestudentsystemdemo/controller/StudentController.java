package com.ubuntuassignment.simplestudentsystemdemo.controller;

import com.ubuntuassignment.simplestudentsystemdemo.entity.Student;
import com.ubuntuassignment.simplestudentsystemdemo.service.StudentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentManager studentManager;

    @RequestMapping(value = "allstudent", method = RequestMethod.GET)
    public ResponseEntity<?> getAllStudents(){
        try{
            return ResponseEntity.ok(studentManager.getAllStudents());
        }
        catch (Exception e){
            return ResponseEntity.status(500).body("Error retrieving students: " + e.getMessage());
        }

    }

    @RequestMapping(value = "studentbyid/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(studentManager.getStudentById(id));
    }

    @RequestMapping(value = "addstudent", method = RequestMethod.POST)
    public ResponseEntity<?> addStudent(@RequestBody Student student){
        studentManager.addStudent(student);
        return ResponseEntity.ok(student);
    }

    @RequestMapping(value = "updatestudent/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student){
        Student updatedStudent = studentManager.updateStudent(id, student);
        return ResponseEntity.ok(updatedStudent);
    }

    @RequestMapping(value = "deletestudent/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        Student deletedStudent = studentManager.deleteStudentById(id);
        return ResponseEntity.ok(deletedStudent);
    }
}
