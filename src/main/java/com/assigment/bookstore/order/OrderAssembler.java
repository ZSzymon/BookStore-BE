package com.assigment.bookstore.order;

import com.assigment.bookstore.order.models.Order;
import com.assigment.bookstore.order.models.OrderDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderAssembler implements RepresentationModelAssembler<Order, EntityModel<OrderDto>> {

    @Override
    public @NotNull EntityModel<OrderDto> toModel(@NotNull Order order){
        OrderDto orderDto = new OrderDto(order);
        return EntityModel.of(orderDto,
                linkTo(methodOn(OrderController.class).one(order.getClientEmail())).withSelfRel(),
                linkTo(methodOn(OrderController.class).all()).withRel("books"));
    }

    @Override
    public CollectionModel<EntityModel<OrderDto>> toCollectionModel(Iterable<? extends Order> entities) {
        var orders = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel).toList();
        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }
}
