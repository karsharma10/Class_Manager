package com.ClassProject.ClassProject.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassDto {
    private Long id;

    private String name;

    StudentDto studentDto;

}
