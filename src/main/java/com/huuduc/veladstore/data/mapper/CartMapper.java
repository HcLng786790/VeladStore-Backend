package com.huuduc.veladstore.data.mapper;

import com.huuduc.veladstore.data.dto.CartDTOResponse;
import com.huuduc.veladstore.data.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartDTOResponse toDTO(Cart cart);
}
