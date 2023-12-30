package com.ClassProject.ClassProject.services.implementations;

import com.ClassProject.ClassProject.domain.entities.StudentEntity;
import com.ClassProject.ClassProject.respositories.StudentRespository;
import com.ClassProject.ClassProject.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentServiceImpl implements StudentService {

    StudentRespository studentRespository;

    public StudentServiceImpl(StudentRespository studentRespository){
        this.studentRespository = studentRespository;
    }
    @Override
    public StudentEntity save(StudentEntity studentEntity) {
        return studentRespository.save(studentEntity);
    }

    @Override
    public List<StudentEntity> getAllStudents() {
        Iterable<StudentEntity> all = studentRespository.findAll();
        List<StudentEntity> findAllStudents = StreamSupport.stream(all.spliterator(), false).toList();

        return findAllStudents;
    }

    @Override
    public boolean isExists(Long id) {
        return studentRespository.existsById(id);
    }

    @Override
    public StudentEntity fullUpdate(StudentEntity studentEntity, Long id) {
       studentEntity.setId(id);

       return studentRespository.save(studentEntity);
    }
}
