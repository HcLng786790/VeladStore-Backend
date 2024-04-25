package com.huuduc.veladstore.data.mapper;

import com.huuduc.veladstore.data.dto.img.ImageDTOResponse;
import com.huuduc.veladstore.data.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    ImageDTOResponse toDTO(Image image);
}
