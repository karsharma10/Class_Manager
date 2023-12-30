package com.ClassProject.ClassProject.respositories;


import com.ClassProject.ClassProject.domain.entities.ClassEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends CrudRepository<ClassEntity, Long> {
    List<ClassEntity> findByStudentEntity_Id(Long id);
}
