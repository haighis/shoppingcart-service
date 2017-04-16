package com.shoppingcartservice.web.rest;

import com.shoppingcartservice.ShoppingcartserviceApp;
import com.shoppingcartservice.domain.Cart;
import com.shoppingcartservice.repository.CartRepository;
import com.shoppingcartservice.service.CartService;
import com.shoppingcartservice.web.rest.dto.CartDTO;
import com.shoppingcartservice.web.rest.mapper.CartMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CartResource REST controller.
 *
 * @see CartResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ShoppingcartserviceApp.class)
@WebAppConfiguration
@IntegrationTest
public class CartResourceIntTest {

    private static final String DEFAULT_CUSTOMER_ID = "ABABABA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 9;
    private static final Integer UPDATED_QUANTITY = 8;
    
    private static final String DEFAULT_SKU = "X78RTYE";
    private static final String UPDATED_SKU = "A8A45ER";
    
    private static final String DEFAULT_TITLE = "TEST";
    private static final String UPDATED_TITLE = "ESTE";

    @Inject
    private CartRepository cartRepository;

    @Inject
    private CartMapper cartMapper;

    @Inject
    private CartService cartService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCartMockMvc;

    private Cart cart;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CartResource cartResource = new CartResource();
        ReflectionTestUtils.setField(cartResource, "cartService", cartService);
        ReflectionTestUtils.setField(cartResource, "cartMapper", cartMapper);
        this.restCartMockMvc = MockMvcBuilders.standaloneSetup(cartResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cart = new Cart();
        cart.setCustomerId(DEFAULT_CUSTOMER_ID);
        cart.setQuantity(DEFAULT_QUANTITY);
        cart.setSku(DEFAULT_SKU);
        cart.setTitle(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createCart() throws Exception {
        // Arrange
    	int databaseSizeBeforeCreate = cartRepository.findAll().size();

        // Create the Cart
        CartDTO cartDTO = cartMapper.cartToCartDTO(cart);
        
        // Act
        restCartMockMvc.perform(post("/api/carts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO)))
                .andExpect(status().isCreated());
        
        // Assert
        // Validate the Cart in the database
        List<Cart> carts = cartRepository.findAll();
        assertThat(carts).hasSize(databaseSizeBeforeCreate + 1);
        Cart testCart = carts.get(carts.size() - 1);
        assertThat(testCart.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testCart.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCart.getSku()).isEqualTo(DEFAULT_SKU);
        assertThat(testCart.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartRepository.findAll().size();
        // set the field null
        cart.setCustomerId(null);

        // Create the Cart, which fails.
        CartDTO cartDTO = cartMapper.cartToCartDTO(cart);

        restCartMockMvc.perform(post("/api/carts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO)))
                .andExpect(status().isBadRequest());

        List<Cart> carts = cartRepository.findAll();
        assertThat(carts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartRepository.findAll().size();
        // set the field null
        cart.setQuantity(null);

        // Create the Cart, which fails.
        CartDTO cartDTO = cartMapper.cartToCartDTO(cart);

        restCartMockMvc.perform(post("/api/carts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO)))
                .andExpect(status().isBadRequest());

        List<Cart> carts = cartRepository.findAll();
        assertThat(carts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSkuIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartRepository.findAll().size();
        // set the field null
        cart.setSku(null);

        // Create the Cart, which fails.
        CartDTO cartDTO = cartMapper.cartToCartDTO(cart);

        restCartMockMvc.perform(post("/api/carts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO)))
                .andExpect(status().isBadRequest());

        List<Cart> carts = cartRepository.findAll();
        assertThat(carts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void updateCart() throws Exception {
    	// Arrange
        // Initialize the database
        cartRepository.saveAndFlush(cart);
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart
        Cart updatedCart = new Cart();
        updatedCart.setId(cart.getId());
        updatedCart.setCustomerId(UPDATED_CUSTOMER_ID);
        updatedCart.setQuantity(UPDATED_QUANTITY);
        updatedCart.setSku(UPDATED_SKU);
        updatedCart.setTitle(UPDATED_TITLE);
        
        // Act
        CartDTO cartDTO = cartMapper.cartToCartDTO(updatedCart);

        restCartMockMvc.perform(put("/api/carts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cartDTO)))
                .andExpect(status().isOk());

    	// Assert
        // Validate the Cart in the database
        List<Cart> carts = cartRepository.findAll();
        assertThat(carts).hasSize(databaseSizeBeforeUpdate);
        Cart testCart = carts.get(carts.size() - 1);
        assertThat(testCart.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCart.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCart.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCart.getSku()).isEqualTo(UPDATED_SKU);
    }

    @Test
    @Transactional
    public void deleteCart() throws Exception {
    	// Arrange
        // Initialize the database
        cartRepository.saveAndFlush(cart);
        int databaseSizeBeforeDelete = cartRepository.findAll().size();

        // Act
        // Get the cart
        restCartMockMvc.perform(delete("/api/carts/{id}", cart.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Assert
        // Validate the database is empty
        List<Cart> carts = cartRepository.findAll();
        assertThat(carts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
