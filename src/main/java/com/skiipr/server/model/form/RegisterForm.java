/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.form;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;

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
     private Boolean captcha;

    
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
    public void setModel (ModelMap modelMap){
         modelMap.addAttribute("nameValue",name);
         modelMap.addAttribute("userNameValue",username);
         modelMap.addAttribute("passwordValue",password);
         modelMap.addAttribute("emailValue",email);
         modelMap.addAttribute("addressNumberStreetValue",addressNumberStreet);
         modelMap.addAttribute("addressPostcodeValue",addressPostcode);
         modelMap.addAttribute("suburbValue",suburb);
         modelMap.addAttribute("addressStateValue",addressState);
         modelMap.addAttribute("addressCountryValue",addressCountry);
         modelMap.addAttribute("paypalValue",paypal);
         modelMap.addAttribute("websiteValue",website);
         modelMap.addAttribute("phoneNumberValue",phoneNumber);

        
    }
    
    public boolean validate(MerchantDao merchantDao, Errors errors){
        try{
            if(username.isEmpty()){
                errors.rejectValue("username", "invalid.registration.username.null");
            }
            if(!username.isEmpty() && !merchantDao.userNameAvailableNoSession(username)){
                errors.rejectValue("username", "invalid.registration.username.alreadyexists");
            }
            if(name.isEmpty()){
                errors.rejectValue("name", "invalid.registration.username.null");
            }
            if(!name.isEmpty() && !merchantDao.tradingNameAvailableNoSession(name)){
                errors.rejectValue("name", "invalid.registration.name.alreadyexists");
            }
            if(!password.isEmpty() && !(password.equals(password2))){
                errors.rejectValue("password", "invalid.password.match");
            }
            if(password.isEmpty()){
                 errors.rejectValue("password", "invalid.password.null");
            }
            if(!email.matches("^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$")){
                errors.rejectValue("email","invalid.register.email");
            }
            if(!addressPostcode.matches("^[1-9]{1}[0-9]{3}$")){
                errors.rejectValue("addressPostcode", "invalid.postcode.size");
            }
            if(!paypal.matches("^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$")){
                errors.rejectValue("paypal", "invalid.register.paypal");
            }
            if(website.contains("http")){
                errors.rejectValue("website", "invalid.website.address");
            }
            if(!captcha){
                errors.rejectValue("captcha", "incorrect.captcha");
            }
            
            
            return !errors.hasErrors();
        } catch(Exception e){
            System.out.println("Ohhh nooo");
            return false;
        }
       
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

    /**
     * @return the captcha
     */
    public Boolean getCaptcha() {
        return captcha;
    }

    /**
     * @param captcha the captcha to set
     */
    public void setCaptcha(Boolean captcha) {
        this.captcha = captcha;
    }
     
    
}
