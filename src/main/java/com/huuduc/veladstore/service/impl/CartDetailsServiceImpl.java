package com.huuduc.veladstore.service.impl;

import com.huuduc.veladstore.data.dto.cartdetails.CartDetailsDTORequest;
import com.huuduc.veladstore.data.dto.cartdetails.CartDetailsDTOResponse;
import com.huuduc.veladstore.data.entity.Cart;
import com.huuduc.veladstore.data.entity.CartDetails;
import com.huuduc.veladstore.data.entity.Product;
import com.huuduc.veladstore.data.mapper.CartDetailsMapper;
import com.huuduc.veladstore.exception.NotFoundException;
import com.huuduc.veladstore.exception.NullException;
import com.huuduc.veladstore.exception.OutOfStockException;
import com.huuduc.veladstore.repository.CartDetailsRepository;
import com.huuduc.veladstore.repository.CartRepository;
import com.huuduc.veladstore.repository.ProductRepository;
import com.huuduc.veladstore.service.CartDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CartDetailsServiceImpl implements CartDetailsService {

    @Autowired
    private CartDetailsRepository cartDetailsRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailsMapper cartDetailsMapper;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartDetailsDTOResponse addToCart(long cartId, CartDetailsDTORequest cartDetailsDTORequest) {

        Map<String, Object> errors = new HashMap<>();

        //Find cart with cart id on this request
        Cart findCart = this.cartRepository.findById(cartId).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Cart id", cartId))
        );

        if (ObjectUtils.isEmpty(cartDetailsDTORequest.getQuantity())) {
            errors.put("quantity", null);
        }
        if (ObjectUtils.isEmpty(cartDetailsDTORequest.getProductId())) {
            errors.put("Product id", null);
        }

        if (errors.size() > 0) {
            throw new NullException(Collections.singletonMap("Request", errors));
        }

        //Find product with product id on this request
        Product findProduct = this.productRepository.findById(cartDetailsDTORequest.getProductId())
                .orElseThrow(
                        () -> new NotFoundException(Collections.singletonMap("Product id", cartDetailsDTORequest.getProductId()))
                );

        //Check status product is true
        if (!findProduct.isStatus()) {
            throw new NotFoundException(Collections.singletonMap("Product id", cartDetailsDTORequest.getProductId()));
        }

        //Check quantity had out of stock
        if (findProduct.getStock() == 0 || findProduct.getStock() < cartDetailsDTORequest.getQuantity()) {
            throw new OutOfStockException(Collections.singletonMap("Quantity out of stock", findProduct.getStock()));
        }

        CartDetailsDTOResponse cartDetailsDTOResponse = new CartDetailsDTOResponse();

        //Check product have exits on cart
        List<CartDetails> cartDetailsList = findCart.getCartDetailsList();
        boolean found = false;
        for (CartDetails cartDetails : cartDetailsList) {

            //Product had exits then update cart line item
            if (cartDetails.getProduct().getId().equals(findProduct.getId())) {

                int newQuantity = cartDetails.getQuantity() + cartDetailsDTORequest.getQuantity();

                //Check quantity had out of stock
                if (findProduct.getStock() < newQuantity) {
                    throw new OutOfStockException(Collections.singletonMap("Product stock", findProduct.getStock()));
                }

                //Start method update cart details
                updateCartDetails(cartDetails, cartDetailsDTORequest.getQuantity(), newQuantity);

                this.cartDetailsRepository.save(cartDetails);
                cartDetailsDTOResponse = this.cartDetailsMapper.toDTO(cartDetails);
                found = true;
                break;
            }
        }

        //Product had not exits then create new cart line item
        if (!found) {
            CartDetails cartDetails = this.cartDetailsMapper.toEntity(cartDetailsDTORequest);

            cartDetails.setCart(findCart);
            cartDetails.setProduct(findProduct);
            cartDetails.setStatus(true);
            cartDetails.setTotalPrice(cartDetails.getQuantity() * findProduct.getPrice());

            this.cartDetailsRepository.save(cartDetails);

            cartDetailsDTOResponse = this.cartDetailsMapper.toDTO(cartDetails);
        }

        return cartDetailsDTOResponse;
    }

    @Override
    public CartDetailsDTOResponse updateById(long cartDetailsId, CartDetailsDTORequest cartDetailsDTORequest) {

        //Check cart details exit
        CartDetails findCartDetails = this.cartDetailsRepository.findById(cartDetailsId)
                .orElseThrow(
                        () -> new NotFoundException(Collections.singletonMap("Cart Details id", cartDetailsId))
                );

        if (cartDetailsDTORequest.getQuantity() == 0) {
            this.cartDetailsRepository.delete(findCartDetails);
            return null;
        }

        findCartDetails.setQuantity(cartDetailsDTORequest.getQuantity());
        double newTotalPrice = cartDetailsDTORequest.getQuantity() * findCartDetails.getProduct().getPrice();
        findCartDetails.setTotalPrice(newTotalPrice);

        this.cartDetailsRepository.save(findCartDetails);

        return this.cartDetailsMapper.toDTO(findCartDetails);
    }

    @Override
    public List<CartDetailsDTOResponse> findByCartId(long cartId) {

        Cart findCart = this.cartRepository.findById(cartId).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Cart id", cartId))
        );

        List<CartDetails> cartDetailsList = this.cartDetailsRepository.findByCartId(cartId);

        for (CartDetails cartDetails : cartDetailsList) {
            if (!cartDetails.getProduct().isStatus()) {
                cartDetails.setStatus(false);
            }
        }

        List<CartDetailsDTOResponse> cartDetailsDTOResponse = cartDetailsList.stream()
                .map(c -> this.cartDetailsMapper.toDTO(c)).toList();

        return cartDetailsDTOResponse;

    }


    public void updateCartDetails(CartDetails cartDetails, int quantity, int newQuantity) {

        cartDetails.setQuantity(newQuantity);
        cartDetails.setTotalPrice(cartDetails.getProduct().getPrice() * newQuantity);

    }

    public void updateStockProduct(Product product, int quantity) {

        int oldStock = product.getStock();
        product.setStock(oldStock - quantity);
        this.productRepository.save(product);
    }
}
