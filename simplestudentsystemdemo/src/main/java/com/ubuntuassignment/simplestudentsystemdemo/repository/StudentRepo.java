package com.ubuntuassignment.simplestudentsystemdemo.repository;

import com.ubuntuassignment.simplestudentsystemdemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {

}
