package com.huuduc.veladstore.service;

import com.huuduc.veladstore.data.dto.PaginationDTO;
import com.huuduc.veladstore.data.dto.type.TypeDTOResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TypeService {

    TypeDTOResponse create(String name);

    PaginationDTO findAll(int no,int limit);

    boolean deleteById(Long id);

    TypeDTOResponse findById(long id);

    List<TypeDTOResponse> searchByName(String name);

    TypeDTOResponse updateByName(long id,String name);
}
