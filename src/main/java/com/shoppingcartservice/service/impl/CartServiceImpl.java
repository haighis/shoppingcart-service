package com.shoppingcartservice.service.impl;

import com.shoppingcartservice.service.CartService;
import com.shoppingcartservice.domain.Cart;
import com.shoppingcartservice.repository.CartRepository;
import com.shoppingcartservice.web.rest.dto.CartDTO;
import com.shoppingcartservice.web.rest.mapper.CartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Cart.
 */
@Service
@Transactional
public class CartServiceImpl implements CartService{

    private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
    
    @Inject
    private CartRepository cartRepository;
    
    @Inject
    private CartMapper cartMapper;
    
    /**
     * Save a cart.
     * 
     * @param cartDTO the entity to save
     * @return the persisted entity
     */
    public CartDTO save(CartDTO cartDTO) {
        log.debug("Request to save Cart : {}", cartDTO);
        Cart cart = cartMapper.cartDTOToCart(cartDTO);
        cart = cartRepository.save(cart);
        CartDTO result = cartMapper.cartToCartDTO(cart);
        return result;
    }

    /**
     *  Get one cart by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CartDTO findOne(Long id) {
        log.debug("Request to get Cart : {}", id);
        Cart cart = cartRepository.findOne(id);
        CartDTO cartDTO = cartMapper.cartToCartDTO(cart);
        return cartDTO;
    }

    /**
     *  Delete the  cart by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cart : {}", id);
        cartRepository.delete(id);
    }
}
