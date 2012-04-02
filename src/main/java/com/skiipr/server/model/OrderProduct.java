package com.skiipr.server.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "tbl_orderproduct")
public class OrderProduct {
    @Id
    private Long orderProductID;
    private Product product;
    @ManyToOne
    private Order userOrder;
    private int quantity;
    private String productName;
    @DecimalMin("0")
    private BigDecimal productPrice;

    public Long getOrderProductID() {
        return orderProductID;
    }

    public void setOrderProductID(Long orderProductID) {
        this.orderProductID = orderProductID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.productName = product.getName();
        this.productPrice = product.getPrice();
    }

    @JsonIgnore
    public Order getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(Order userOrder) {
        this.userOrder = userOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    
}
