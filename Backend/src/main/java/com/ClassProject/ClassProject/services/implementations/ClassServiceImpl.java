package com.ClassProject.ClassProject.services.implementations;

import com.ClassProject.ClassProject.domain.entities.ClassEntity;
import com.ClassProject.ClassProject.respositories.ClassRepository;
import com.ClassProject.ClassProject.services.ClassService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class ClassServiceImpl implements ClassService {

    private ClassRepository classRepository;

    public ClassServiceImpl(ClassRepository classRepository){
        this.classRepository = classRepository;
    }
    @Override
    public ClassEntity save(ClassEntity classEntity) {
        return classRepository.save(classEntity);
    }

    @Override
    public List<ClassEntity> getClassesForStudent(ClassEntity classEntity) {
        List<ClassEntity> results = classRepository.findByStudentEntity_Id(classEntity.getStudentEntity().getId());
        return results;
    }

    @Override
    public List<ClassEntity> getAllClasses() {
        Iterable<ClassEntity> results = classRepository.findAll();

        return StreamSupport.stream(results.spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public boolean isExists(Long id) {
        return classRepository.existsById(id);
    }

    @Override
    public ClassEntity fullUpdate(ClassEntity classEntity, Long id) {
       classEntity.setId(id);
       return classRepository.save(classEntity);
    }
}
