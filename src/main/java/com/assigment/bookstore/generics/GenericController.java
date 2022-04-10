package com.assigment.bookstore.generics;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
public class GenericController<T, TDto> {

    protected final IGenericService<T, TDto> service;


    @GetMapping()
    public CollectionModel<?> all(){
        return service.all();
    }


    @GetMapping("{title}")
    public ResponseEntity<?> one(@PathVariable String title) {
        return service.getBy(title);
    }


    @PostMapping("")
    public ResponseEntity<?> addOne(@RequestBody TDto tdto){
        return service.addOne(tdto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{name}")
    public ResponseEntity<?> removeOne(@PathVariable String name){
        return service.removeOne(name);
    }
}
