package com.assigment.bookstore.order;

import com.assigment.bookstore.IGenericService;
import com.assigment.bookstore.order.models.Order;
import com.assigment.bookstore.order.models.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService implements IGenericService<Order, OrderDto> {

    OrderRepository orderRepository;
    OrderAssembler modelAssembler;
    @Override
    public CollectionModel<?> all() {
        return modelAssembler.toCollectionModel(orderRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getBy(String name) {
        return null;
    }

    @Override
    public ResponseEntity<?> removeOne(String name) {
        return null;
    }

    @Override
    public ResponseEntity<?> addOne(OrderDto orderDto) {
        return null;
    }
}
