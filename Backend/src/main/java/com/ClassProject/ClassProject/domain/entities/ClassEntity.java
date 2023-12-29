package com.ClassProject.ClassProject.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "classes")

public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_id_generator")
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL) //when we retrieve the author from the database, if we make changes to the author when we retrieve it, those changes will be consistent.
    @JoinColumn(name = "student_id")
    private StudentEntity studentEntity;

}

