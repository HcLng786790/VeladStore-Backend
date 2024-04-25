package com.huuduc.veladstore.controller;

import com.huuduc.veladstore.common.constraint.ApiUrl;
import com.huuduc.veladstore.common.constraint.PageDefault;
import com.huuduc.veladstore.data.dto.product.ProductDTORequest;
import com.huuduc.veladstore.exception.NullException;
import com.huuduc.veladstore.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(ApiUrl.PRODUCT)
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create(@RequestPart(required = false) ProductDTORequest productDTORequest,
                                    @RequestPart(name = "fileImages", required = false) List<MultipartFile> fileImgaes){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.productService.create(productDTORequest,fileImgaes));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = PageDefault.NO) int no,
                                    @RequestParam(defaultValue = PageDefault.LIMIT) int limit){

        return ResponseEntity.ok(productService.getAll(no,limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(required = false) long id){

        if(ObjectUtils.isEmpty(id)){
            throw new NullException(Collections.singletonMap("id",null));
        }

        return ResponseEntity.ok(this.productService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam(required = false) String name){

        return ResponseEntity.ok(this.productService.findByName(name));

    }

    @Transactional
    @PutMapping(value = "/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateProduct(@PathVariable(required = false) long id,
                                           @RequestPart ProductDTORequest productDTORequest,
                                           @RequestPart(required = false,name = "fileImages") List<MultipartFile> fileImages){
        return ResponseEntity.ok(this.productService.update(id,productDTORequest,fileImages));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(required = false) Long id){

        this.productService.deleteById(id);

        return ResponseEntity.ok("Soft deleted successfully");
    }

    @GetMapping("/category")
    public ResponseEntity<?> findAllByCategory(@RequestParam(required = false) Long categoryId,
                                               @RequestParam(defaultValue = PageDefault.NO) int no,
                                               @RequestParam(defaultValue = PageDefault.LIMIT) int limit){

        if(categoryId==null){
            throw new NullException(Collections.singletonMap("Category id",null));
        }

        return ResponseEntity.ok(this.productService.findByCategory(categoryId,no,limit));

    }

    @GetMapping("/type")
    public ResponseEntity<?> findAllByTye(@RequestParam(required = false) Long typeId,
                                               @RequestParam(defaultValue = PageDefault.NO) int no,
                                               @RequestParam(defaultValue = PageDefault.LIMIT) int limit){

        if(typeId==null){
            throw new NullException(Collections.singletonMap("Type id",null));
        }

        return ResponseEntity.ok(this.productService.findByType(typeId,no,limit));

    }
}
