package com.ClassProject.ClassProject.controllers;

import com.ClassProject.ClassProject.domain.dto.ClassDto;
import com.ClassProject.ClassProject.domain.entities.ClassEntity;
import com.ClassProject.ClassProject.mappers.Mapper;
import com.ClassProject.ClassProject.services.ClassService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClassController {
    //Need mapper to map Dto to Entities
    private Mapper<ClassEntity, ClassDto> classMapper;

    //Need Service layer to get CRUD functionality
    private ClassService classService;

    public ClassController(Mapper<ClassEntity, ClassDto> classMapper, ClassService classService){
        this.classMapper = classMapper;
        this.classService = classService;
    }

    @PostMapping(path = "/class")
    public ClassDto createClass(@RequestBody ClassDto classDto){
        ClassEntity formattedClassEntity = classMapper.mapFrom(classDto);
        ClassEntity resultClassEntity = classService.save(formattedClassEntity);

        return classMapper.mapTo(resultClassEntity);
    }

    @GetMapping(path = "/classesForStudent")
    public List<ClassDto> getClassesForSpecificStudent(@RequestBody ClassDto classDto){
        ClassEntity formattedClassEntity = classMapper.mapFrom(classDto);
        List<ClassEntity> results = classService.getClassesForStudent(formattedClassEntity);

        return results.stream().map(classEntity -> classMapper.mapTo(classEntity)).collect(Collectors.toList());
    }

    @GetMapping(path ="/allClasses")
    public List<ClassDto> getAllClasses() {
        List<ClassEntity> allClassesEntities = classService.getAllClasses();

        return allClassesEntities.stream().map(classEntity -> classMapper.mapTo(classEntity)).collect(Collectors.toList());
    }


}
