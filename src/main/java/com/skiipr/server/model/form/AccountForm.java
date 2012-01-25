package com.skiipr.server.model.form;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import org.springframework.validation.Errors;

public class AccountForm{
    private String username;
    private String password;
    private String password2;
    private String email;

    public AccountForm(Merchant merchant){
        username = merchant.getUsername();
        email = merchant.getEmail();
    }

    public AccountForm() {}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setAttributes(Merchant merchant){
        merchant.setEmail(email);
        merchant.setUsername(username);
    }
    
    public boolean validate(MerchantDao dao, Errors errors){
        if(!dao.usernameAvailable(username)){
            errors.rejectValue("username", "invalid.username.conflict");
        }
        if(!password.isEmpty() && !(password.equals(password2))){
            errors.rejectValue("password", "invalid.password.match");
        }
        return !errors.hasErrors();
    }
}