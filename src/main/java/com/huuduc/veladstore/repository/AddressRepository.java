package com.huuduc.veladstore.repository;

import com.huuduc.veladstore.data.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    Address findByUserIdAndDefaultsIsTrue(long id);

    Page<Address> findByUserIdAndStatusIsTrue(long id, Pageable pageable);
}
