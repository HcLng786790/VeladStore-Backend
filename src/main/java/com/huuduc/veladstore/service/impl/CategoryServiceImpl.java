package com.huuduc.veladstore.service.impl;

import com.huuduc.veladstore.data.dto.category.CategoryDTOResponse;
import com.huuduc.veladstore.data.dto.PaginationDTO;
import com.huuduc.veladstore.data.entity.Category;
import com.huuduc.veladstore.data.mapper.CategoryMapper;
import com.huuduc.veladstore.exception.ConflicExeception;
import com.huuduc.veladstore.exception.NotFoundException;
import com.huuduc.veladstore.exception.NullException;
import com.huuduc.veladstore.repository.CategoryRepository;
import com.huuduc.veladstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryDTOResponse create(String name) {

        //Check null param
        if(name==null || ObjectUtils.isEmpty(name)){
            throw new NullException(Collections.singletonMap("Name category:",null));
        }

        //Check exits name
        if(this.categoryRepository.existsByName(name)){
            throw new ConflicExeception(Collections.singletonMap("Name category:",name));
        }

        Category category = new Category();
        category.setName(name);
        category.setStatus(true);
        this.categoryRepository.save(category);

        return this.categoryMapper.toDTO(category);
    }

    @Override
    public PaginationDTO getAll(int no, int limit) {

        Page<CategoryDTOResponse> page = this.categoryRepository.findAllByStatusIsTrue(PageRequest.of(no,limit))
                .map(p -> this.categoryMapper.toDTO(p));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(),
                page.getTotalElements(), page.getSize(), page.getNumber());

    }

    @Override
    public boolean deleteById(Long id) {

        //Check exits category
        Category category = this.categoryRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Category id",id))
        );

        if(!category.isStatus()){
            throw new NotFoundException(Collections.singletonMap("Category id",id));
        }

        category.setStatus(false);

        this.categoryRepository.save(category);

        return true;
    }

    @Override
    public CategoryDTOResponse findById(long id) {

        Category category = this.categoryRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Category id",id))
        );

        if(!category.isStatus()){
            throw new NotFoundException(Collections.singletonMap("Category id",id));
        }

        return this.categoryMapper.toDTO(category);

    }

    @Override
    public List<CategoryDTOResponse> searchByName(String name) {

        if(name==null||ObjectUtils.isEmpty(name)){
            throw new NullException(Collections.singletonMap("Name",null));
        }

        List<Category> categories = this.categoryRepository.findByNameContaining(name);

        for (int i=0;i<categories.size();i++){
            if (!categories.get(i).isStatus()){
                categories.remove(categories.get(i));
            }
        }

        if(categories.size()==0){
            throw new NotFoundException(Collections.singletonMap("Name",name));
        }

        List<CategoryDTOResponse> categoryDTOResponses = categories.stream()
                .map(c -> this.categoryMapper.toDTO(c)).toList();

        return categoryDTOResponses;
    }

    @Override
    public CategoryDTOResponse updateById(long id,String name) {

        Category category = this.categoryRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Category id",id))
        );

        if(!category.isStatus()){
            throw new NotFoundException(Collections.singletonMap("Category id",id));
        }

        category.setName(name);

        this.categoryRepository.save(category);

        return this.categoryMapper.toDTO(category);

    }
}
