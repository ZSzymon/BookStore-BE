package com.assigment.bookstore;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
public class GenericController<T, TDto> {

    private final IGenericService<T, TDto> service;

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

    @DeleteMapping("{name}")
    public ResponseEntity<?> removeOne(@PathVariable String name){
        return service.removeOne(name);
    }
}
