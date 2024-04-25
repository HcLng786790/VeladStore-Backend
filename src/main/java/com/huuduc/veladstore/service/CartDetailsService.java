package com.huuduc.veladstore.service;

import com.huuduc.veladstore.data.dto.cartdetails.CartDetailsDTORequest;
import com.huuduc.veladstore.data.dto.cartdetails.CartDetailsDTOResponse;
import com.huuduc.veladstore.data.entity.CartDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartDetailsService {
    CartDetailsDTOResponse addToCart(long cartId, CartDetailsDTORequest cartDetailsDTORequest);

    CartDetailsDTOResponse updateById(long cartDetailsId,CartDetailsDTORequest cartDetailsDTORequest);

    List<CartDetailsDTOResponse> findByCartId(long cartId);
}
