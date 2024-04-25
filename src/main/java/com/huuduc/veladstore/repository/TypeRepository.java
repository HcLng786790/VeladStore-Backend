package com.huuduc.veladstore.repository;

import com.huuduc.veladstore.data.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type,Long> {

    boolean existsByName(String name);

    Optional<Type> findByName(String name);

    Page<Type> findAllByStatusIsTrue(Pageable pageable);

    Optional<Type> findById(long id);

    List<Type> findByNameContaining(String name);
}
