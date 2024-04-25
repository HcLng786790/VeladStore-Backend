package com.huuduc.veladstore.service.impl;

import com.huuduc.veladstore.data.dto.PaginationDTO;
import com.huuduc.veladstore.data.dto.type.TypeDTOResponse;
import com.huuduc.veladstore.data.entity.Type;
import com.huuduc.veladstore.data.mapper.TypeMapper;
import com.huuduc.veladstore.exception.ConflicExeception;
import com.huuduc.veladstore.exception.NotFoundException;
import com.huuduc.veladstore.exception.NullException;
import com.huuduc.veladstore.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

@Component
public class TypeServiceImpl implements com.huuduc.veladstore.service.TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public TypeDTOResponse create(String name) {


        //Check null or empty param
        if(name==null){
            throw new NullException(Collections.singletonMap("Name Type",null));
        }

        //check if conflict name
        if(this.typeRepository.existsByName(name)){
            throw new ConflicExeception(Collections.singletonMap("name",name));
        }

        Type newType = new Type();

        newType.setName(name);
        newType.setStatus(true);

        this.typeRepository.save(newType);

        return this.typeMapper.toDTO(newType);
    }

    @Override
    public PaginationDTO findAll(int no,int limit) {

        Page<TypeDTOResponse> page = this.typeRepository.findAllByStatusIsTrue(PageRequest.of(no,limit))
                .map(p -> this.typeMapper.toDTO(p));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(),
                page.getTotalElements(), page.getSize(), page.getNumber());

    }

    @Override
    public boolean deleteById(Long id) {

        //Check null PathVariable
        if (id == null) {
            throw new NullException(Collections.singletonMap("id", null));
        }

        //Check database have any type with id on request
        Type type = this.typeRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("id", id))
        );

        //Check if the product has been removed
        if (!type.isStatus()) {
            throw new NotFoundException(Collections.singletonMap("id", id));
        }

        type.setStatus(false);
        typeRepository.save(type);

        return true;
    }

    @Override
    public TypeDTOResponse findById(long id) {

        //Check have exists type in database
        Type foundType = this.typeRepository.findById(id).orElseThrow(
                ()->new NotFoundException(Collections.singletonMap("Type id: ",id))
        );

        if(!foundType.isStatus()){
            throw new NotFoundException(Collections.singletonMap("Type id",id));
        }

        return this.typeMapper.toDTO(foundType);
    }

    @Override
    public List<TypeDTOResponse> searchByName(String name) {

        //Check null param
        if(name == null || ObjectUtils.isEmpty(name)){
            throw new NullException(Collections.singletonMap("Name:",null));
        }

        //Get list type with containing name
        List<Type> list = this.typeRepository.findByNameContaining(name);

        //Remove type have status false
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isStatus()) {
                list.remove(list.get(i));
            }
        }

        if(list.size()==0){
            throw new NotFoundException(Collections.singletonMap("Name:",name));
        }

        List<TypeDTOResponse> typeDTOResponses = list
                .stream().map(t -> this.typeMapper.toDTO(t)).toList();

        return typeDTOResponses;
    }

    @Override
    public TypeDTOResponse updateByName(long id,String name) {

        //Check null param
        if(name==null || ObjectUtils.isEmpty(name)||ObjectUtils.isEmpty(id)){
            throw new NullException(Collections.singletonMap("Param request",null));
        }

        Type foundType = this.typeRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Collections.singletonMap("Type",id))
        );

        if(!foundType.isStatus()){
            throw new NotFoundException(Collections.singletonMap("Type",id));
        }

        foundType.setName(name);

        this.typeRepository.save(foundType);

        return this.typeMapper.toDTO(foundType);
    }
}
