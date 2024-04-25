package com.huuduc.veladstore.data.mapper;

import com.huuduc.veladstore.data.dto.user.RegisterDTORequest;
import com.huuduc.veladstore.data.dto.user.RegisterDTOResponse;
import com.huuduc.veladstore.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "status",constant = "true")
    User toEntity(RegisterDTORequest registerDTORequest);

    RegisterDTOResponse toDTO(User user);
}
