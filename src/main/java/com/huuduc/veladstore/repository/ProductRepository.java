package com.huuduc.veladstore.repository;

import com.huuduc.veladstore.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsByName(String  name);

    Page<Product> findByStatusIsTrue(Pageable pageable);

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    List<Product> findByNameContaining(String name);

    Page<Product> findByCategoryIdAndStatusIsTrue(Long id,Pageable pageable);

    Page<Product> findByTypeIdAndStatusIsTrue(Long id,Pageable pageable);
}
