package com.kenuiworks.frameworkbox.mapper;

import com.kenuiworks.frameworkbox.dto.FrameworkDTO;
import com.kenuiworks.frameworkbox.model.Framework;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

//@Mapper
public interface FrameworkMapper {

    FrameworkMapper INSTANCE = Mappers.getMapper(FrameworkMapper.class);

    Framework toModel(FrameworkDTO frameworkDTO);

    FrameworkDTO toDTO(Framework framework);
}
