package com.huuduc.veladstore.repository;

import com.huuduc.veladstore.data.entity.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails,Long> {

    Optional<CartDetails> findById(long id);

    List<CartDetails> findByCartId(long cartId);
}
