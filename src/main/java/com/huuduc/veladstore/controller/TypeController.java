package com.huuduc.veladstore.controller;

import com.huuduc.veladstore.common.constraint.ApiUrl;
import com.huuduc.veladstore.common.constraint.PageDefault;
import com.huuduc.veladstore.exception.NullException;
import com.huuduc.veladstore.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping(ApiUrl.TYPE)
public class TypeController {

    @Autowired
    private TypeService typeService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam(required = false) String name){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.typeService.create(name));
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = PageDefault.NO) int no,
                                     @RequestParam(defaultValue = PageDefault.LIMIT) int limit){
        return ResponseEntity.ok(this.typeService.findAll(no,limit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(required = false) Long id){

        this.typeService.deleteById(id);

        return ResponseEntity.ok("Soft delete type success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){

        return ResponseEntity.ok(this.typeService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam String name){

        return ResponseEntity.ok(this.typeService.searchByName(name));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateById(@RequestParam Long id,
                                        @RequestParam String name){

        if(id==null){
            throw new NullException(Collections.singletonMap("id",null));
        }
        return ResponseEntity.ok(this.typeService.updateByName(id,name));

    }

}
