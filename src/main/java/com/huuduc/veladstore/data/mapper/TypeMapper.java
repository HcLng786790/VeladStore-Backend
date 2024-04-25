package com.huuduc.veladstore.data.mapper;

import com.huuduc.veladstore.data.dto.type.TypeDTOResponse;
import com.huuduc.veladstore.data.entity.Type;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    TypeDTOResponse toDTO(Type type);
}
