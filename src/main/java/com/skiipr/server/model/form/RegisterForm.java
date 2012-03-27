/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.form;

import com.skiipr.server.model.Merchant;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 *
 * @author Michael
 */
public class RegisterForm {
     private String username;
     private String email;
     private String password;
     private String password2;
     private String name;
     private String suburb;
     private String addressNumberStreet;
     private String addressPostcode;
     private String addressState;
     private String addressCountry;
     private String paypal;
     private String website;
     private String phoneNumber;

    
    public String getUsername() {
        return username;
    }

    
    public void setUsername(String username) {
        this.username = username;
    }

    
    public String getPassword() {
        return password;
    }

    
    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getPassword2() {
        return password2;
    }

    
    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public String getSuburb() {
        return suburb;
    }

    
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    
    public String getAddressNumberStreet() {
        return addressNumberStreet;
    }

    
    public void setAddressNumberStreet(String addressNumberStreet) {
        this.addressNumberStreet = addressNumberStreet;
    }

    
    public String getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
    }

    
    public String getAddressState() {
        return addressState;
    }

    
    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    
    public String getAddressCountry() {
        return addressCountry;
    }

    
    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    
    public String getPaypal() {
        return paypal;
    }

    
    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }

    
    public String getWebsite() {
        return website;
    }

    
    public void setWebsite(String website) {
        this.website = website;
    }

    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setAttributes(Merchant merchant){
        merchant.setUsername(username);
        merchant.setSalt("1234");
        PasswordEncoder passwordEncoder = new ShaPasswordEncoder();
        String passwordEncoded = passwordEncoder.encodePassword(password, merchant.getSalt());
        
        merchant.setPassword(passwordEncoded);
        merchant.setAddressCountry(addressCountry);
        merchant.setAddressNumberStreet(addressNumberStreet);
        merchant.setAddressPostcode(addressPostcode);
        merchant.setAddressState(addressState);
        merchant.setSuburb(suburb);
        merchant.setPaypal(paypal);
        merchant.setWebsite(website);
        merchant.setPhoneNumber(phoneNumber);
        merchant.setEmail(email);
        merchant.setName(name);
    }
    
    public boolean validate(){
        return true;
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
     
    
}
