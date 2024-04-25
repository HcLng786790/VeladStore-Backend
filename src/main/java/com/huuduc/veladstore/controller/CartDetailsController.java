package com.huuduc.veladstore.controller;

import com.huuduc.veladstore.common.constraint.ApiUrl;
import com.huuduc.veladstore.data.dto.cartdetails.CartDetailsDTORequest;
import com.huuduc.veladstore.service.CartDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrl.CARTDETAIL)
public class CartDetailsController {

    @Autowired
    private CartDetailsService cartDetailsService;

    @PostMapping("/addtocart/{cartId}")
    public ResponseEntity<?> addToCart(@PathVariable long cartId,
                                       @RequestBody CartDetailsDTORequest cartDetailsDTORequest){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.cartDetailsService.addToCart(cartId,cartDetailsDTORequest));
    }

    @PutMapping("/updateCart/{cartDetailsId}")
    public ResponseEntity<?> updateCartDetails(@PathVariable long cartDetailsId,
                                               @RequestBody CartDetailsDTORequest cartDetailsDTORequest){

        if(this.cartDetailsService.updateById(cartDetailsId,cartDetailsDTORequest)==null){
            return ResponseEntity.ok("Delete cart details sucess");
        }
        return ResponseEntity.ok(this.cartDetailsService.updateById(cartDetailsId,cartDetailsDTORequest));
    }

    @GetMapping("/all/{cartId}")
    public ResponseEntity<?> findByCartId(@PathVariable long cartId){

        return ResponseEntity.ok(this.cartDetailsService.findByCartId(cartId));
    }
}
