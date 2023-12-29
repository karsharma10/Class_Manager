package com.ClassProject.ClassProject.utils;

import com.ClassProject.ClassProject.domain.entities.ClassEntity;
import com.ClassProject.ClassProject.domain.entities.StudentEntity;

public class TestData {
    public static StudentEntity createStudentA() {
        return StudentEntity.builder()
                .id(1L)
                .first_name("StudentAFirst")
                .last_name("StudentALast")
                .age(45)
                .build();
    }

    public static StudentEntity createStudentB() {
        return StudentEntity.builder()
                .id(2L)
                .first_name("StudentBFirst")
                .last_name("StudentBLast")
                .age(50)
                .build();
    }

    public static StudentEntity createStudentC() {
        return StudentEntity.builder()
                .id(3L)
                .first_name("StudentCFirst")
                .last_name("StudentCLast")
                .age(55)
                .build();
    }

    public static ClassEntity createClassA(final StudentEntity studentEntity){
            return ClassEntity.builder()
                    .id(1L)
                    .name("Math Class")
                    .studentEntity(studentEntity)
                    .build();
    }
}
