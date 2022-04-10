package com.assigment.bookstore.bookOrder;

import com.assigment.bookstore.bookOrder.models.BookOrder;
import com.assigment.bookstore.bookOrder.models.BookOrderDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookOrderAssembler implements RepresentationModelAssembler<BookOrder, EntityModel<BookOrderDto>> {

    @Override
    public @NotNull EntityModel<BookOrderDto> toModel(@NotNull BookOrder bookOrder){
        BookOrderDto bookOrderDto = new BookOrderDto(bookOrder);
        return EntityModel.of(bookOrderDto,
                linkTo(methodOn(BookOrderController.class).one(bookOrder.getClientEmail())).withSelfRel(),
                linkTo(methodOn(BookOrderController.class).all()).withRel("books"));
    }

    @Override
    public CollectionModel<EntityModel<BookOrderDto>> toCollectionModel(Iterable<? extends BookOrder> entities) {
        var orders = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel).toList();
        return CollectionModel.of(orders,
                linkTo(methodOn(BookOrderController.class).all()).withSelfRel());
    }
}
