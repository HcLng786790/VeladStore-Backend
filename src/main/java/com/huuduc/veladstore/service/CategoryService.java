package com.huuduc.veladstore.service;

import com.huuduc.veladstore.data.dto.category.CategoryDTOResponse;
import com.huuduc.veladstore.data.dto.PaginationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    CategoryDTOResponse create(String name);

    PaginationDTO getAll(int no,int limit);

    boolean deleteById(Long id);

    CategoryDTOResponse findById(long id);

    List<CategoryDTOResponse> searchByName(String name);

    CategoryDTOResponse updateById(long id,String name);
}
