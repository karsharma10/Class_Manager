package com.ClassProject.ClassProject.controllers;

import com.ClassProject.ClassProject.domain.dto.StudentDto;
import com.ClassProject.ClassProject.domain.entities.StudentEntity;
import com.ClassProject.ClassProject.mappers.Mapper;
import com.ClassProject.ClassProject.services.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private StudentService studentService;
    private Mapper<StudentEntity, StudentDto> studentMapper;
    public StudentController(StudentService studentService, Mapper<StudentEntity, StudentDto> studentMapper ){
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @PostMapping(path = "/students")
    public StudentDto saveStudent(@RequestBody StudentDto studentDto){
        StudentEntity studentEntityRequest = studentMapper.mapFrom(studentDto);
        StudentEntity studentEntityResults = studentService.save(studentEntityRequest);
        return studentMapper.mapTo(studentEntityResults);
    }

    @GetMapping(path = "/students")
    public List<StudentDto> getStudents(){
        List<StudentEntity> studentEntityList = studentService.getAllStudents();
        List<StudentDto> convertResult = studentEntityList.stream().map(studentEntity -> studentMapper.mapTo(studentEntity)).collect(Collectors.toList());

        return convertResult;
    }

}
