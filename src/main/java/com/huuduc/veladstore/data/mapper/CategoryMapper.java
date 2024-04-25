package com.huuduc.veladstore.data.mapper;

import com.huuduc.veladstore.data.dto.category.CategoryDTOResponse;
import com.huuduc.veladstore.data.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTOResponse toDTO(Category category);
}
