package com.assigment.bookstore.person;


import com.assigment.bookstore.cart.Cart;
import com.assigment.bookstore.cart.CartController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This class is used for generating links each Person object.
 */
@Component
public class CartAssembler implements RepresentationModelAssembler<Cart, EntityModel<Cart>> {

    @Override
    public EntityModel<Cart> toModel(Cart cart){
        return EntityModel.of(cart,
                linkTo(methodOn(CartController.class).one(cart.getPersonEmail())).withSelfRel(),
                linkTo(methodOn(CartController.class).all()).withRel("carts"));
    }

    @Override
    public CollectionModel<EntityModel<Cart>> toCollectionModel(Iterable<? extends Cart> entities) {
        var carts = StreamSupport.stream(entities.spliterator(), false).map(this::toModel).toList();
        return CollectionModel.of(carts,
                linkTo(methodOn(CartController.class).all()).withSelfRel());
    }
}
