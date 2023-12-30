package com.ClassProject.ClassProject.services.implementations;

import com.ClassProject.ClassProject.domain.entities.ClassEntity;
import com.ClassProject.ClassProject.respositories.ClassRepository;
import com.ClassProject.ClassProject.services.ClassService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
