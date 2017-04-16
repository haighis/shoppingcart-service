package com.shoppingcartservice.repository;

import com.shoppingcartservice.domain.Cart;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cart entity.
 */
@SuppressWarnings("unused")
public interface CartRepository extends JpaRepository<Cart,Long> {

}
