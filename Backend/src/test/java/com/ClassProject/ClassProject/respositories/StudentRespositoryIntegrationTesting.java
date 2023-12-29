package com.ClassProject.ClassProject.respositories;


import com.ClassProject.ClassProject.domain.entities.StudentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentRespositoryIntegrationTesting {

    private StudentRespository underTests;

    @Autowired
    StudentRespositoryIntegrationTesting(StudentRespository studentRespository){
        this.underTests = studentRespository;
    }

    @Test
    public void studentCanBeRecalledAndCreated(){

        StudentEntity testStudentA = StudentEntity.builder()
                .id(1L)
                .first_name("StudentAFirst")
                .last_name("StudentALast")
                .age(45)
                .build();

        underTests.save(testStudentA);

        Optional<StudentEntity> results = underTests.findById(testStudentA.getId());
        assertThat(results).isPresent();
        assertThat(results.get()).isEqualTo(testStudentA);
    }



}