package com.assigment.bookstore.order;

import com.assigment.bookstore.generics.GenericController;
import com.assigment.bookstore.order.models.Order;
import com.assigment.bookstore.order.models.OrderDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController extends GenericController<Order, OrderDto> {


    private final OrderService orderService;

    public OrderController(OrderService _orderService) {
        super(_orderService);
        orderService = _orderService;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public CollectionModel<?> all(){
        return service.all();
    }

    @Override
    public ResponseEntity<?> addOne(@RequestBody OrderDto tdto) {

        return super.addOne(tdto);
    }

    @Override
    @GetMapping("{email}")
    public ResponseEntity<?> one(@PathVariable String email) {
        return service.getBy(email);
    }

}
