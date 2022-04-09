package com.assigment.bookstore.order;

import com.assigment.bookstore.IGenericService;
import com.assigment.bookstore.book.Book;
import com.assigment.bookstore.exceptions.NotFoundException;
import com.assigment.bookstore.order.models.Order;
import com.assigment.bookstore.order.models.OrderDto;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return ResponseEntity.ok(modelAssembler.toModel(orderRepository
                .findByClientEmail(name)
                .orElseThrow(()->new NotFoundException("Order", name))));
    }

    @Override
    public ResponseEntity<?> removeOne(String name) {
        orderRepository.removeOrderByClientEmail(name);
        return ResponseEntity.ok("");
    }

    @Override
    @Deprecated
    public ResponseEntity<?> addOne(OrderDto orderDto) {
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> addOne(OrderDto orderDto, String email) {
        orderRepository.findByClientEmail(email).ifPresentOrElse(
                o -> {
                    if(orderDto.getBookList()==null){return;}
                    List<Book> oldOrderList = o.getOrderList();
                    oldOrderList.addAll(orderDto.getBookList());
                    o.setOrderList(oldOrderList);
                    orderRepository.save(o);

                }, ()->{
                    orderRepository.save(new Order(orderDto, email));
                }
        );
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
}
