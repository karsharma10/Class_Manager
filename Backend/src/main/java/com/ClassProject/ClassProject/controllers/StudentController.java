package com.ClassProject.ClassProject.controllers;

import com.ClassProject.ClassProject.domain.dto.StudentDto;
import com.ClassProject.ClassProject.domain.entities.StudentEntity;
import com.ClassProject.ClassProject.mappers.Mapper;
import com.ClassProject.ClassProject.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/students/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id){
        if(!studentService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            StudentEntity results = studentService.getStudent(id);
            StudentDto convertResults = studentMapper.mapTo(results);
            return new ResponseEntity<>(convertResults,HttpStatus.OK);
        }
    }

    @PostMapping(path = "/students/{id}")
    public ResponseEntity<StudentDto> fullUpdate(@RequestBody StudentDto studentDto, @PathVariable Long id){
        if(!studentService.isExists(id)){ //doesn't exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{ //it does exist so do a full update
            StudentEntity convertedStudent = studentMapper.mapFrom(studentDto);
            StudentEntity result = studentService.fullUpdate(convertedStudent, id);

            return new ResponseEntity<>(studentMapper.mapTo(result), HttpStatus.OK);
        }
    }
    @DeleteMapping(path = "/students/{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id){
        if(!studentService.isExists(id)){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            studentService.deleteStudent(id);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

}
