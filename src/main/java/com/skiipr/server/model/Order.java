/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

import com.skiipr.server.enums.OrderStatus;
import com.skiipr.server.enums.OrderType;
import com.skiipr.server.enums.PaymentType;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;


@Entity
@Table(name = "tbl_order")
public class Order implements Serializable {
        
    @Id
    private Long orderID;
    private Long orderTime;
    private Merchant merchant;
    @OneToMany
    private Set<OrderProduct> orderProducts;
    private String paypalAddress;
    private Long paypalRef;
    private OrderType orderType;
    private OrderStatus status;
    private Long lastUpdated;
    private float total;
    private String email;
    private String deviceID;
    private PaymentType paymentType;
    private String apid;
    
    @JsonIgnore
    public Date getFormattedOrderTime() {
        Date formattedDate = new Date(orderTime * 1000);
        return formattedDate;
    }
    
    @JsonIgnore
    public Date getFormattedLastUpdated(){
        Date formattedDate = new Date(lastUpdated * 1000);
        return formattedDate;
    }
    
    @JsonIgnore
    public String getFormattedTotal(){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(total);
    }

    public void setOrderID(Long id){
        this.orderID = id;
    }
    
    public void setOrderTime(Long time){
        this.orderTime = time;
    }
    
    public void setPaypalAddress(String description){
        this.paypalAddress = description ;
    }
    
    public void setPaypalRef(Long ref){
        this.paypalRef = ref;
    }
    
    public void setOrderType(OrderType type){
        this.orderType = type;
        
    }
    
    public void setStatus(OrderStatus status){
        this.status = status;
    }
    
    public void setLastUpdated(Long update){
        this.lastUpdated = update;
    }
    
    public void setTotal(float total){
        this.total = total;
    }
    
    public Long getOrderID(){
        return orderID;
    }
    
    public Long getOrderTime(){
        return orderTime;
    }
    
    public Long getMerchantID(){
        return merchant.getMerchantID();
    }
    
    @JsonIgnore
    public Merchant getMerchant(){
        return merchant;
    }
    
    public void setMerchant(Merchant merchant){
        this.merchant = merchant;
    }
    
    @JsonIgnore
    public String getPaypalAddress(){
        return paypalAddress;
    }
    
    @JsonIgnore
    public Long getPaypalRef(){
        return paypalRef;
    }
    
    public OrderType getOrderType(){
        return orderType;
        
    }
    
    public OrderStatus getStatus(){
        return status;
    }
    
    public Long getLastUpdated(){
        return lastUpdated;
    }
    
    public float getTotal(){
        return total;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @JsonIgnore
    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    
    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @JsonIgnore
    public String getApid() {
        return apid;
    }

    public void setApid(String apid) {
        this.apid = apid;
    }
}
