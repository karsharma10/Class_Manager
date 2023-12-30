package com.ClassProject.ClassProject.services;

import com.ClassProject.ClassProject.domain.entities.StudentEntity;

import java.util.List;

public interface StudentService {
    StudentEntity save(StudentEntity studentEntity);

    List<StudentEntity> getAllStudents();

    boolean isExists(Long id);

    StudentEntity fullUpdate(StudentEntity studentEntity, Long id);
}
