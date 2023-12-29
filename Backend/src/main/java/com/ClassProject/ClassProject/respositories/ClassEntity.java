package com.ClassProject.ClassProject.respositories;


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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private StudentEntity studentEntity;

}
