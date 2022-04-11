package com.assigment.bookstore.bookOrder;

import com.assigment.bookstore.generics.GenericController;
import com.assigment.bookstore.bookOrder.models.BookOrder;
import com.assigment.bookstore.bookOrder.models.BookOrderDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/bookorders")
public class BookOrderController extends GenericController<BookOrder, BookOrderDto> {


    private final BookOrderService bookOrderService;

    public BookOrderController(BookOrderService _Book_orderService) {
        super(_Book_orderService);
        bookOrderService = _Book_orderService;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public CollectionModel<?> all(){
        return service.all();
    }

    @Override
    public ResponseEntity<?> addOne(@RequestBody BookOrderDto tdto) {

        return super.addOne(tdto);
    }

    @Override
    @GetMapping("{email}")
    public ResponseEntity<?> one(@PathVariable String email) {
        return service.getBy(email);
    }

}
