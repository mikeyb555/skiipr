package com.skiipr.server.model.validators;

import com.skiipr.server.model.Merchant;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MerchantValidator implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return Merchant.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Merchant merchant = (Merchant) o;
        if((!merchant.getAddressPostcode().matches("^[1-9]{1}[0-9]{3}$"))){
            errors.rejectValue("addressPostcode", "invalid.postcode.size");
        }
        if(merchant.getWebsite().contains("http")){

            errors.rejectValue("website", "invalid.website.address");
        }
        if((merchant.getPhoneNumber().length() < 8) || (merchant.getPhoneNumber().length() > 20)){
            errors.rejectValue("phoneNumber", "invalid.phonenumber.size");
        }
        if(!merchant.getEmail().matches("^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$")){
            errors.rejectValue("email", "invalid.email.address");
            
        }
        if(!merchant.getLatitude().matches("^-?([1-8]?[1-9]|[1-9]0)\\.{1}\\d{1,6}")){
            errors.rejectValue("latitude", "invalid.latitude");
        }
        if(!merchant.getLatitude().matches("^-?([1]?[1-7][1-9]|[1]?[1-8][0]|[1-9]?[0-9])\\.{1}\\d{1,6}")){
            errors.rejectValue("latitude", "invalid.longitude");
        }
         
            
        
    }

}
