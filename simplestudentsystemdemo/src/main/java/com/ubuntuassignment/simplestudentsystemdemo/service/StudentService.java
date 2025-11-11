package com.ubuntuassignment.simplestudentsystemdemo.service;

import com.ubuntuassignment.simplestudentsystemdemo.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();


    List<Student> getStudentById(Long id);

    public Student addStudent(Student student);

    public Student updateStudent(Long id, Student student);

    public Student deleteStudentById(Long id);
}
