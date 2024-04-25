package com.huuduc.veladstore.service;

import com.huuduc.veladstore.data.dto.PaginationDTO;
import com.huuduc.veladstore.data.dto.product.ProductDTORequest;
import com.huuduc.veladstore.data.dto.product.ProductDTOResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {

    ProductDTOResponse create(ProductDTORequest productDTORequest, List<MultipartFile> fileImages);

    PaginationDTO getAll(int no, int limit);

    boolean deleteById(Long id);

    ProductDTOResponse findById(Long id);

    List<ProductDTOResponse> findByName(String name);

    ProductDTOResponse update(long id,ProductDTORequest productDTORequest, List<MultipartFile> fileImages);

    PaginationDTO findByCategory(long id,int no , int limit);

    PaginationDTO findByType(long id,int no , int limit);
}
