package com.ubuntuassignment.simplestudentsystemdemo.service;

import com.ubuntuassignment.simplestudentsystemdemo.entity.Student;
import com.ubuntuassignment.simplestudentsystemdemo.repository.StudentRepo;
import com.ubuntuassignment.simplestudentsystemdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentManager implements StudentService {

    @Autowired
    private StudentRepo studentRepo;
    public List<Student> getAllStudents(){
        return studentRepo.findAll();
    }

    public List<Student> getStudentById(Long id){
        return studentRepo.findAllById(List.of(id));
    }

    public Student addStudent(Student student){
        return studentRepo.save(student);
    }

    public Student updateStudent(Long id, Student student){
        Student existingStudent = studentRepo.findById(id).orElse(null);
        if(existingStudent != null){
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setAge(student.getAge());
            return studentRepo.save(existingStudent);
        }
        return null;
    }

    public Student deleteStudentById(Long id){
        Student existingStudent = studentRepo.findById(id).orElse(null);
        if(existingStudent != null){
            studentRepo.deleteById(id);
            return existingStudent;
        }
        return null;
    }
}
