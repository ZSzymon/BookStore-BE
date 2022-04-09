package com.assigment.bookstore;

import com.assigment.bookstore.order.models.OrderDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public interface IGenericService<T, TDTO> {

     CollectionModel<?> all();

     ResponseEntity<?> getBy(@PathVariable String name);

     ResponseEntity<?> removeOne(@PathVariable String name);

     ResponseEntity<?> addOne(TDTO tdto);


}
