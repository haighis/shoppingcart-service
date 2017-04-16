package com.shoppingcartservice.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Cart entity.
 * 
 * Cart Item roughly maps to a Product and Order Detail line item
 * via Sku (business key)
 */
public class CartDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 64)
    private String customerId;

    @NotNull
    @Max(value = 9)
    private Integer quantity;

    @NotNull
    @Size(max = 64)
    private String sku;

    @NotNull
    @Size(max = 128)
    private String title;

    /**
     * The cart identifier
     * @return
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * The Customer Id of the Cart item
     * @return
     */
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    /**
     * The Quantity of the Item in the cart.
     * @return
     */
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    /**
     * The Cart Sku
     * @return
     */
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
    
    /**
     * The Title of the Cart Item.
     * @return
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CartDTO cartDTO = (CartDTO) o;

        if ( ! Objects.equals(id, cartDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CartDTO{" +
            "id=" + id +
            ", customerId='" + customerId + "'" +
            ", quantity='" + quantity + "'" +
            ", sku='" + sku + "'" +
            ", title='" + title + "'" +
            '}';
    }
}
