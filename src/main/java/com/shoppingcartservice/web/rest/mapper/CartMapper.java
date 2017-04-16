package com.shoppingcartservice.web.rest.mapper;

import com.shoppingcartservice.domain.*;
import com.shoppingcartservice.web.rest.dto.CartDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Cart and its DTO CartDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CartMapper {

    CartDTO cartToCartDTO(Cart cart);

    List<CartDTO> cartsToCartDTOs(List<Cart> carts);

    Cart cartDTOToCart(CartDTO cartDTO);

    List<Cart> cartDTOsToCarts(List<CartDTO> cartDTOs);
}
