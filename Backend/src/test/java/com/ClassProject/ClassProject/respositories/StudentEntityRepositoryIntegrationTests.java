package com.ClassProject.ClassProject.respositories;

import com.ClassProject.ClassProject.utils.TestData;
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
public class StudentEntityRepositoryIntegrationTests {

    private StudentRespository underTests;
    private ClassRepository classRepository;
    @Autowired
    StudentEntityRepositoryIntegrationTests(StudentRespository studentRespository, ClassRepository classRepository){
        this.underTests = studentRespository;
        this.classRepository = classRepository;
    }

    @Test
    public void studentCanBeRecalledAndCreated(){
        StudentEntity testStudentA = TestData.createStudentA();
        underTests.save(testStudentA);

        Optional<StudentEntity> results = underTests.findById(testStudentA.getId());
        assertThat(results).isPresent();
        assertThat(results.get()).isEqualTo(testStudentA);
    }

    @Test
    public void multipleStudentsCanBeCreatedAndRecalled(){
        StudentEntity testStudentEntityA = TestData.createStudentA();
        StudentEntity testStudentEntityB = TestData.createStudentB();
        StudentEntity testStudentEntityC = TestData.createStudentC();

        underTests.save(testStudentEntityA);
        underTests.save(testStudentEntityB);
        underTests.save(testStudentEntityC);

        Iterable<StudentEntity> results = underTests.findAll();
        assertThat(results).hasSize(3);
        assertThat(results).contains(testStudentEntityA, testStudentEntityB, testStudentEntityC);
    }

    @Test
    public void testThatStudentCanBeCreatedAndUpdated(){
        StudentEntity testStudentEntityA = TestData.createStudentA();

        underTests.save(testStudentEntityA);
        testStudentEntityA.setFirst_name("TestA");

        underTests.save(testStudentEntityA);

        Optional<StudentEntity> results = underTests.findById(testStudentEntityA.getId());

        assertThat(results).isPresent();
        assertThat(results.get().getFirst_name()).isEqualTo("TestA");
    }

    @Test
    public void testThatStudentCanBeCreatedAndUDeleted(){
        StudentEntity testStudentA = TestData.createStudentA();

        underTests.save(testStudentA);
        underTests.deleteById(testStudentA.getId());

        Optional<StudentEntity> results = underTests.findById(testStudentA.getId());

        assertThat(results).isEmpty();

    }


}
