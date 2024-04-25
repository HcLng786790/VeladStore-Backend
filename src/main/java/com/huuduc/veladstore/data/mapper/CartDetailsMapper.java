package com.huuduc.veladstore.data.mapper;

import com.huuduc.veladstore.data.dto.cartdetails.CartDetailsDTORequest;
import com.huuduc.veladstore.data.dto.cartdetails.CartDetailsDTOResponse;
import com.huuduc.veladstore.data.entity.CartDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {ProductMapper.class,CategoryMapper.class, TypeMapper.class,ImageMapper.class})
public interface CartDetailsMapper {

    @Mapping(ignore = true,target = "cart")
    @Mapping(ignore = true,target = "product")
    @Mapping(ignore = true,target = "totalPrice")
    CartDetails toEntity(CartDetailsDTORequest cartDetailsDTORequest);

    @Mapping(source = "cart",target = "cartDTOResponse")
    @Mapping(source = "product",target = "productDTOResponse")
    CartDetailsDTOResponse toDTO(CartDetails cartDetails);
}
