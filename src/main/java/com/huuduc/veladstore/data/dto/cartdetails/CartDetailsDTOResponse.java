package com.huuduc.veladstore.data.dto.cartdetails;

import com.huuduc.veladstore.data.dto.CartDTOResponse;
import com.huuduc.veladstore.data.dto.product.ProductDTOResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailsDTOResponse {

    private Long id;
    private int quantity;
    private double totalPrice;
    private ProductDTOResponse productDTOResponse;
    private CartDTOResponse cartDTOResponse;
    private boolean status;
}
