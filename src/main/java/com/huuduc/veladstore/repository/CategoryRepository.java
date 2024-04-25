package com.huuduc.veladstore.repository;

import com.huuduc.veladstore.data.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    boolean existsByName(String name);

    Optional<Category> findByName(String name);

    boolean existsById(long id);

    Page<Category> findAllByStatusIsTrue(Pageable pageable);

    List<Category> findByNameContaining(String name);
}
