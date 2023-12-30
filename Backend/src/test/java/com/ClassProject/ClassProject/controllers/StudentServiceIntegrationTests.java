package com.ClassProject.ClassProject.controllers;

import com.ClassProject.ClassProject.domain.entities.StudentEntity;
import com.ClassProject.ClassProject.respositories.StudentRespository;
import com.ClassProject.ClassProject.utils.TestData;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.awt.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class StudentServiceIntegrationTests {

    private StudentRespository studentRespository;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public StudentServiceIntegrationTests(StudentRespository studentRespository, MockMvc mockMvc){
        this.studentRespository = studentRespository;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void StudentControllerReturnsCorrectHttpStatusWhenSavingStudent() throws Exception {
        StudentEntity testStudentA = TestData.createStudentA();
        String json = objectMapper.writeValueAsString(testStudentA);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void StudentControllerReturnsCorrectObjectWhenCreatingStudent() throws Exception{
       StudentEntity testStudentA = TestData.createStudentA();
       String jsonStudentA = objectMapper.writeValueAsString(testStudentA);

       mockMvc.perform(post("/students")
               .contentType(MediaType.APPLICATION_JSON)
               .content(jsonStudentA)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.first_name").value("StudentAFirst"))
               .andExpect(jsonPath("$.last_name").value("StudentALast"))
               .andExpect(jsonPath("$.age").value(45));
    }

    @Test
    public void StudentControllerReturnsTheCorrectHttpStatusWhenGettingStudents() throws Exception {
        mockMvc.perform(get("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void StudentControllerReturnsTheCorrectJsonObjectsWhenGettingStudents() throws Exception{
        StudentEntity testStudentEntityA = TestData.createStudentA();

        studentRespository.save(testStudentEntityA); //should be saved in the database now.

        mockMvc.perform(get("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].first_name").value(testStudentEntityA.getFirst_name()))
                .andExpect(jsonPath("$[0].last_name").value(testStudentEntityA.getLast_name()))
                .andExpect(jsonPath("$[0].age").value(testStudentEntityA.getAge()));

    }
}
