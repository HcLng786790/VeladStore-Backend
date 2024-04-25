package com.huuduc.veladstore.controller;

import com.huuduc.veladstore.common.constraint.ApiUrl;
import com.huuduc.veladstore.common.constraint.PageDefault;
import com.huuduc.veladstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrl.CATEGORY)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam String name){

        return ResponseEntity.status(HttpStatus.CREATED).body(this.categoryService.create(name));
    }

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = PageDefault.NO) int no,
                                    @RequestParam(defaultValue = PageDefault.LIMIT) int limit){
        return ResponseEntity.ok(this.categoryService.getAll(no,limit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){

        this.categoryService.deleteById(id);

        return ResponseEntity.ok("Soft delete type success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){

        return ResponseEntity.ok(this.categoryService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam String name){

        return ResponseEntity.ok(this.categoryService.searchByName(name));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateById(@RequestParam long id,
                                        @RequestParam String name){

        return ResponseEntity.ok(this.categoryService.updateById(id,name));
    }
}
