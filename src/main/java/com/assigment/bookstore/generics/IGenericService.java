package com.assigment.bookstore.generics;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public interface IGenericService<T, TDTO> {

     CollectionModel<?> all();

     ResponseEntity<?> getBy(@PathVariable String name);

     ResponseEntity<?> removeOne(@PathVariable String name);

     ResponseEntity<?> addOne(TDTO tdto);


}
