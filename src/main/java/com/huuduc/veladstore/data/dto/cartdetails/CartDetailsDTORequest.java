package com.huuduc.veladstore.data.dto.cartdetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailsDTORequest {

    private Long productId;
    private int quantity;
}
