package com.huuduc.veladstore.data.dto.product;

import com.huuduc.veladstore.data.dto.category.CategoryDTOResponse;
import com.huuduc.veladstore.data.dto.img.ImageDTOResponse;
import com.huuduc.veladstore.data.dto.type.TypeDTOResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTOResponse {

    private String id;
    private String name;
    private double price;
    private int stock;
    private TypeDTOResponse typeDTOResponse;
    private CategoryDTOResponse categoryDTOResponse;
    private List<ImageDTOResponse> imageDTOResponses;
}
