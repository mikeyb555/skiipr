/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "tbl_merchant")
public class Merchant implements Serializable {
    @NotBlank
    @Length(max = 80)
    private String username;
    private String password;
    private Long merchantID;
    private int loginCount;
    private int failedLoginTime;
    private int lastLoginTime;
    private String salt;
    private String name;
    private String addressNumber;
    private String addressStreet;
    private String addressPostcode;
    private String addressState;
    private String addressCountry;
    private String paypal;
    private String latitude;
    private String longitude;
    @Email
    private String email;
    private int planId;
    private Plan plan;
    private Set categories;
    
    @JsonIgnore
    public String getUsername(){
        return username;
    }
    
    @JsonIgnore
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    @Id
    public Long getMerchantID(){
        return merchantID;
    }
    
    public void setSalt(String salt){
        this.salt = salt;
    }
    
    public void setMerchantID(Long id){
        merchantID = id;
    }
    
    public void setLoginCount(int loginCount){
        this.loginCount = loginCount;
    }
    
    public void setFailedLoginTime(int time){
        failedLoginTime = time;
    }
    
    public void setLastLoginTime(int time){
        lastLoginTime = time;
    }
    
    @JsonIgnore
    public int getLoginCount(){
        return loginCount;
    }
    
    @JsonIgnore
    public int getFailedLoginTime(){
        return failedLoginTime;
    }
    
    @JsonIgnore
    public int getLastLoginTime(){
        return lastLoginTime;
    }
    
    @JsonIgnore
    public String getSalt(){
        return salt;
    }
    
    public void noteLoginFailure(){
        setFailedLoginTime((int) (System.currentTimeMillis() / 1000L));
        setLoginCount(getLoginCount() + 1);
    }
    
    public void noteLoginSuccess(){
        setLoginCount(0);
        setLastLoginTime((int) (System.currentTimeMillis() / 1000L));
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the addressNumber
     */
    public String getAddressNumber() {
        return addressNumber;
    }

    /**
     * @param addressNumber the addressNumber to set
     */
    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    /**
     * @return the addressStreet
     */
    public String getAddressStreet() {
        return addressStreet;
    }

    /**
     * @param addressStreet the addressStreet to set
     */
    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    /**
     * @return the addressPostcode
     */
    public String getAddressPostcode() {
        return addressPostcode;
    }

    /**
     * @param addressPostcode the addressPostcode to set
     */
    public void setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
    }

    /**
     * @return the addressCountry
     */
    public String getAddressCountry() {
        return addressCountry;
    }

    /**
     * @param addressCountry the addressCountry to set
     */
    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

     /**
     * @return the addressState
     */
    public String getAddressState() {
        return addressState;
    }

    /**
     * @param addressCountry the addressCountry to set
     */
    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    /**
     * @return the paypal
     */
    public String getPaypal() {
        return paypal;
    }

    /**
     * @param paypal the paypal to set
     */
    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latittude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public int getPlanId() {
        return planId;
    }

    /**
     * @param planId the planId to set
     */
    public void setPlanId(int planId) {
        this.planId = planId;
    }
    
     /**
     * @return the plan
     */
    @JsonIgnore
    @OneToOne
    public Plan getPlan(){
        return plan;
    }

    /**
     * @param plan the plan to set
     */
    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    
    public void setCategories(Set categories){
        this.categories = categories;
    }
    
    public Set getCategories(){
        return categories;
    }
}
