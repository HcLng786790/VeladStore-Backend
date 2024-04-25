package com.huuduc.veladstore.data.mapper;

import com.huuduc.veladstore.data.dto.product.ProductDTORequest;
import com.huuduc.veladstore.data.dto.product.ProductDTOResponse;
import com.huuduc.veladstore.data.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {CategoryMapper.class, TypeMapper.class,ImageMapper.class})
public interface ProductMapper {

    @Mapping(ignore = true,target = "type")
    @Mapping(ignore = true,target = "category")
    Product toEntity(ProductDTORequest productDTORequest);

    @Mapping(source = "type",target = "typeDTOResponse")
    @Mapping(source = "category",target = "categoryDTOResponse")
    @Mapping(source = "images",target = "imageDTOResponses")
    ProductDTOResponse toDTO(Product product);
}
