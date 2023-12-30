package com.ClassProject.ClassProject.services;

import com.ClassProject.ClassProject.domain.entities.ClassEntity;

import java.util.List;

public interface ClassService {
    ClassEntity save(ClassEntity classEntity);

    List<ClassEntity> getClassesForStudent(ClassEntity classEntity);
}
