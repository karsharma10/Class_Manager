package com.ClassProject.ClassProject.controllers;

import com.ClassProject.ClassProject.domain.dto.ClassDto;
import com.ClassProject.ClassProject.domain.dto.StudentDto;
import com.ClassProject.ClassProject.domain.entities.ClassEntity;
import com.ClassProject.ClassProject.domain.entities.StudentEntity;
import com.ClassProject.ClassProject.respositories.ClassRepository;
import com.ClassProject.ClassProject.services.ClassService;
import com.ClassProject.ClassProject.utils.TestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc

public class ClassServiceIntegrationTests {
    //we need object mapper
    ObjectMapper objectMapper;

    //we need a ClassController
    ClassService classService;
    ClassRepository classRepository;
    MockMvc mockMvc;

    @Autowired
    public ClassServiceIntegrationTests(ClassService classService, MockMvc mockMvc, ClassRepository classRepository) {
        this.objectMapper = new ObjectMapper();
        this.mockMvc = mockMvc;
        this.classService = classService;
        this.classRepository = classRepository;
    }

    @Test
    public void testThatClassControllerCreateClassReturnsTheCorrectHttpStatus200() throws Exception{
        ClassEntity testClassAEntity = TestData.createClassA(null);
        String jsonTestClassA = objectMapper.writeValueAsString(testClassAEntity);

        mockMvc.perform(post("/class")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestClassA)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testThatAStudentsClassCanBeRecalled() throws Exception {
        StudentDto studentDtoA = TestData.createStudentADto();

        ClassDto classDtoA = TestData.createClassADto(studentDtoA);

        StudentEntity studentEntityA = TestData.createStudentA();
        ClassEntity testClassAEntity = TestData.createClassA(studentEntityA);
        ClassEntity testClassBEntity = TestData.createClassB(studentEntityA);
        ClassEntity testClassCEntity = TestData.createClassC(studentEntityA);

        classRepository.save(testClassAEntity);
        classRepository.save(testClassBEntity);
        classRepository.save(testClassCEntity);

        String jsonTestClassA = objectMapper.writeValueAsString(classDtoA);

        mockMvc.perform(get("/classesForStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestClassA)
                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(testClassAEntity.getName()))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value(testClassBEntity.getName()))
                .andExpect(jsonPath("$[2].id").value(testClassCEntity.getId()))
                .andExpect(jsonPath("$[2].name").value(testClassCEntity.getName()));
    }

    @Test
    public void testThatAllClassesCanBeReturnedAndHttpStatusIsOk() throws Exception {

        ClassEntity testClassAEntity = TestData.createClassA(null);
        ClassEntity testClassBEntity = TestData.createClassB(null);
        ClassEntity testClassCEntity = TestData.createClassC(null);

        classRepository.save(testClassAEntity);
        classRepository.save(testClassBEntity);
        classRepository.save(testClassCEntity);

        mockMvc.perform(get("/allClasses")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value(testClassAEntity.getId()))
                .andExpect(jsonPath("[0].name").value(testClassAEntity.getName()));
    }

    @Test
    public void testThatUpdateANonExistingClassReturnsCorrectObject() throws Exception{
        ClassEntity testClassEntityA = TestData.createClassA(null);
        classRepository.save(testClassEntityA);

        ClassDto testClassDtoA = TestData.createClassADto(null);
        testClassDtoA.setName("Test Update");
        testClassDtoA.setId(testClassEntityA.getId());


        String json = objectMapper.writeValueAsString(testClassDtoA);

        mockMvc.perform(post("/class/"+ testClassDtoA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testClassDtoA.getId()))
                .andExpect(jsonPath("$.name").value(testClassDtoA.getName()));
    }


    @Test
    public void testThatDeleteExistingClassReturnsTheCorrectHttpStatusOk() throws Exception {
        ClassEntity testClassA = TestData.createClassA(null);
        classRepository.save(testClassA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/class/"+testClassA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatDeleteNonExistingClassReturnsCorrectHttpStatusNotFound() throws Exception{
        ClassEntity testClassB = TestData.createClassB(null);
        classRepository.save(testClassB);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/students/10000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
