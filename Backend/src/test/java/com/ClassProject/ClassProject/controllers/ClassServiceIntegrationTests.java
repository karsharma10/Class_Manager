package com.ClassProject.ClassProject.controllers;

import com.ClassProject.ClassProject.domain.entities.ClassEntity;
import com.ClassProject.ClassProject.domain.entities.StudentEntity;
import com.ClassProject.ClassProject.services.ClassService;
import com.ClassProject.ClassProject.utils.TestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    MockMvc mockMvc;

    @Autowired
    public ClassServiceIntegrationTests(ClassService classService, MockMvc mockMvc) {
        this.objectMapper = new ObjectMapper();
        this.mockMvc = mockMvc;
        this.classService = classService;
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


    //[ClassEntity(id=52, name=Math class, studentEntity=StudentEntity(id=2, first_name=StudentAFirst, last_name=StudentALast, age=45))]

    @Test
    public void testThatAStudentsClassCanBeRecalled(){
        StudentEntity studentEntity = TestData.createStudentA();

    }


}
