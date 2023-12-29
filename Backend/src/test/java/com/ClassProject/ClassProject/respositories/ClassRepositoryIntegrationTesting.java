package com.ClassProject.ClassProject.respositories;


import com.ClassProject.ClassProject.domain.entities.ClassEntity;
import com.ClassProject.ClassProject.domain.entities.StudentEntity;
import com.ClassProject.ClassProject.utils.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClassRepositoryIntegrationTesting {

    private ClassRespository underTests;

    @Autowired
    public ClassRepositoryIntegrationTesting(ClassRespository classRespository){
        this.underTests = classRespository;
    }

    @Test
    public void testThatClassCanBeCreatedAndRecalled(){
        StudentEntity testStudentA = TestData.createStudentA();
        ClassEntity testClassA = TestData.createClassA(testStudentA);

        underTests.save(testClassA);

        Optional<ClassEntity> results = underTests.findById(testClassA.getId());
        assertThat(results).isPresent();
        assertThat(results.get()).isEqualTo(testClassA);
    }

    @Test
    public void testThatMultipleClassesCanBeCreatedAndRecalled(){
        StudentEntity testStudentA = TestData.createStudentA();

        ClassEntity testClassA = TestData.createClassA(testStudentA);
        ClassEntity testClassB = TestData.createClassB(testStudentA);
        ClassEntity testClassC = TestData.createClassC(testStudentA);

        underTests.save(testClassA);
        underTests.save(testClassB);
        underTests.save(testClassC);

        Iterable<ClassEntity> results = underTests.findAll();

        assertThat(results).hasSize(3);
        assertThat(results).contains(testClassA,testClassB,testClassC);

    }

    @Test
    public void testThatClassCanBeCreatedAndUpdated(){
        StudentEntity testStudentA = TestData.createStudentA();

        ClassEntity testClassA = TestData.createClassA(testStudentA);
        underTests.save(testClassA);

        testClassA.setName("Test Class");
        underTests.save(testClassA);

        Optional<ClassEntity> results = underTests.findById(testClassA.getId());

        assertThat(results).isPresent();
        assertThat(results.get().getName()).isEqualTo("Test Class");
    }

    
}
