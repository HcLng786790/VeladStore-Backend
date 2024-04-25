package com.huuduc.veladstore.data.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTORequest {

    private String name;
    private String description;
    private double price;
    private int stock;
    private String typeName;
    private String categoryName;

}
