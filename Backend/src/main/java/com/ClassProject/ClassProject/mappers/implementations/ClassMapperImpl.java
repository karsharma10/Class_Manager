package com.ClassProject.ClassProject.mappers.implementations;

import com.ClassProject.ClassProject.domain.dto.ClassDto;
import com.ClassProject.ClassProject.domain.entities.ClassEntity;
import com.ClassProject.ClassProject.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClassMapperImpl implements Mapper<ClassEntity, ClassDto> {

    private ModelMapper modelMapper;

    public ClassMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public ClassDto mapTo(ClassEntity classEntity) {
        return modelMapper.map(classEntity, ClassDto.class);
    }

    @Override
    public ClassEntity mapFrom(ClassDto classDto) {
        return modelMapper.map(classDto, ClassEntity.class);
    }
}
