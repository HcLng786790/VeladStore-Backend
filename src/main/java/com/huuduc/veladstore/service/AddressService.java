package com.huuduc.veladstore.service;

import com.huuduc.veladstore.data.dto.PaginationDTO;
import com.huuduc.veladstore.data.dto.address.AddressDTORequest;
import com.huuduc.veladstore.data.dto.address.AddressDTOResponse;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {


    AddressDTOResponse create(long userId, AddressDTORequest addressDTORequest);

    PaginationDTO findByUser(long userId,int no,int limit);

    AddressDTOResponse findById(long id);

    AddressDTOResponse updateById(long id, AddressDTORequest addressDTORequest);

    boolean deletedById(long id);
}
