package com.ClassProject.ClassProject.respositories;


import com.ClassProject.ClassProject.domain.entities.ClassEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRespository extends CrudRepository<ClassEntity, Long> {
}
