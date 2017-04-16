package com.shoppingcartservice.service;

import com.shoppingcartservice.domain.Cart;
import com.shoppingcartservice.web.rest.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Cart.
 */
public interface CartService {

    /**
     * Save a cart.
     * 
     * @param cartDTO the entity to save
     * @return the persisted entity
     */
    CartDTO save(CartDTO cartDTO);

    /**
     *  Get the carty by cartId
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    CartDTO findOne(Long id);

    /**
     *  Delete cart by cartId
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
