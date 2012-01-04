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
        if((merchant.getAddressPostcode().length() < 4) || (merchant.getAddressPostcode().length() > 4)){
            errors.rejectValue("addressPostcode", "invalid.postcode.size");
        }
        
    }

}
