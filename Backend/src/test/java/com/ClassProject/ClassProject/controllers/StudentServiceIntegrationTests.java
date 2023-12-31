package com.ClassProject.ClassProject.controllers;

import com.ClassProject.ClassProject.domain.dto.StudentDto;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.delete;
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

    @Test
    public void studentControllerReturnsCorrectHttpStatusWhenTryingToUpdateANonExistingStudent404() throws Exception {

        StudentDto testStudentA = TestData.createStudentADto();
        String json = objectMapper.writeValueAsString(testStudentA);


        mockMvc.perform(post("/students/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void studentControllerReturnsTheCorrectObjectWhenUpdatingAStudent() throws Exception {
        StudentEntity testStudentA = TestData.createStudentA();
        studentRespository.save(testStudentA);

        StudentDto testStudentDtoB = TestData.createStudentBDto();
        testStudentDtoB.setId(testStudentA.getId());

        String jsonTestStudentB = objectMapper.writeValueAsString(testStudentDtoB);

        mockMvc.perform(post("/students/"+testStudentDtoB.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTestStudentB)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testStudentDtoB.getId()))
                .andExpect(jsonPath("$.first_name").value(testStudentDtoB.getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(testStudentDtoB.getLast_name()))
                .andExpect(jsonPath("$.age").value(testStudentDtoB.getAge()));


    }

    @Test
    public void studentControllerReturnsTheCorrectHttpStatusWhenDeletingStudent() throws Exception{
        StudentEntity studentEntityA = TestData.createStudentA();
        studentRespository.save(studentEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/students/"+studentEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testThatDeletingNonExistingStudentReturnsCorrectHttpStatusNotFound() throws Exception{
        StudentEntity testStudentB = TestData.createStudentB();
        studentRespository.save(testStudentB);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/class/10000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetSpecificStudentByIdReturnsCorrectHttpStatusWhenNotFound() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/students/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetSpecificStudentReturnsCorrectHttpStatusWhenFound() throws Exception{

        StudentEntity testStudentA = TestData.createStudentA();
        studentRespository.save(testStudentA);


        mockMvc.perform(
                MockMvcRequestBuilders.get("/students/"+testStudentA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatSpecificStudentReturnsTheCorrectObject() throws Exception{
        StudentEntity testStudentB = TestData.createStudentB();
        studentRespository.save(testStudentB);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/students/"+testStudentB.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
              MockMvcResultMatchers.jsonPath("$.id").value(testStudentB.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.first_name").value(testStudentB.getFirst_name())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.last_name").value(testStudentB.getLast_name())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testStudentB.getAge())
        );
    }

}
