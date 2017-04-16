package com.shoppingcartservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shoppingcartservice.domain.Cart;
import com.shoppingcartservice.service.CartService;
import com.shoppingcartservice.web.rest.util.HeaderUtil;
import com.shoppingcartservice.web.rest.util.PaginationUtil;
import com.shoppingcartservice.web.rest.dto.CartDTO;
import com.shoppingcartservice.web.rest.mapper.CartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Cart.
 * POST - creates a cart
 * GET - gets a cart
 * DELETE - deletes a cart
 */
@RestController
@RequestMapping("/api")
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);
        
    @Inject
    private CartService cartService;
    
    @Inject
    private CartMapper cartMapper;
    
    /**
     * POST  /carts : Create a new cart.
     *
     * @param cartDTO the cartDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cartDTO, or with status 400 (Bad Request) if the cart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/carts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CartDTO> createCart(@Valid @RequestBody CartDTO cartDTO) throws URISyntaxException {
        log.debug("REST request to save Cart : {}", cartDTO);
        if (cartDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cart", "idexists", "A new cart cannot already have an cartId")).body(null);
        }
        CartDTO result = cartService.save(cartDTO);
        return ResponseEntity.created(new URI("/api/carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cart", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carts : Updates an existing cart.
     *
     * @param cartDTO the cartDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cartDTO,
     * or with status 400 (Bad Request) if the cartDTO is not valid,
     * or with status 500 (Internal Server Error) if the cartDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/carts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CartDTO> updateCart(@Valid @RequestBody CartDTO cartDTO) throws URISyntaxException {
        log.debug("REST request to update Cart : {}", cartDTO);
        if (cartDTO.getId() == null) {
            return createCart(cartDTO);
        }
        CartDTO result = cartService.save(cartDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cart", cartDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carts/:id : Gets the card by cartId
     *
     * @param id the cartId of the cartDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cartDTO, or with status 404 (Not Found)
     */
//    @RequestMapping(value = "/carts/{id}",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<CartDTO> getCart(@PathVariable Long id) {
//        log.debug("REST request to get Cart : {}", id);
//        CartDTO cartDTO = cartService.findOne(id);
//        return Optional.ofNullable(cartDTO)
//            .map(result -> new ResponseEntity<>(
//                result,
//                HttpStatus.OK))
//            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    /**
     * DELETE  /carts/:id : Delete a cart by cartId
     *
     * @param id the id of the cartDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/carts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        log.debug("REST request to delete Cart : {}", id);
        cartService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cart", id.toString())).build();
    }
}
