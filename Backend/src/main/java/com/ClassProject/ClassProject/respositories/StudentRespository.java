package com.ClassProject.ClassProject.respositories;

import com.ClassProject.ClassProject.domain.entities.StudentEntity;
import jdk.jfr.Registered;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRespository extends CrudRepository<StudentEntity, Long> {
}
