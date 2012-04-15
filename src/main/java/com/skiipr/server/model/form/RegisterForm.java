
package com.skiipr.server.model.form;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.DAO.PlanDao;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.Plan;
import javax.servlet.http.HttpServletRequest;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

public class RegisterForm {
     private String username;
     private String email;
     private String password;
     private String password2;
     private String captchaChallenge;
     private String captchaResponse;
     private final String privateCaptchaKey = "6Leic88SAAAAANP_e0IxARCNBj0my4NfR-oHApOD";
     
    @Autowired
    private PlanDao planDao;

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

    public Merchant generateMerchant(Plan plan){
        Merchant merchant = new Merchant();
        merchant.setUsername(username);
        merchant.setNewPassword(password);
        merchant.setEmail(email);
        merchant.noteLoginSuccess();
        merchant.setFailedLoginTime((int) (System.currentTimeMillis() / 1000L));
        merchant.setName("");
        merchant.setSuburb("");
        merchant.setAddressNumberStreet("");
        merchant.setAddressPostcode("");
        merchant.setAddressState("");
        merchant.setAddressCountry("USD");
        merchant.setCurrencyType("USD");
        merchant.setPaypal("");
        merchant.setType(0);
        merchant.setOpen(false);
        merchant.setLongitude("");
        merchant.setLatitude("");
        merchant.setPlan(plan);
        merchant.setOpen(false);
        merchant.setPaypalEnabled(false);
        merchant.setCodEnabled(false);
        merchant.setWebsite("");
        merchant.setPhoneNumber("");
        merchant.setLocked(true);
        merchant.setConsoleSoundEnabled(false);
        merchant.setLastChange(System.currentTimeMillis() / 1000L);
        merchant.prepareActivationEmail();
        return merchant;
    }
    
    public boolean validateCaptcha(HttpServletRequest req){
        if(captchaChallenge.isEmpty() || captchaResponse.isEmpty()){
            return false;
        }
        String remoteAddr = req.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey(privateCaptchaKey);
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, captchaChallenge, captchaResponse);
        return reCaptchaResponse.isValid();
    }

    public boolean validate(MerchantDao merchantDao, Errors errors, HttpServletRequest req){
        try{
            if(username.isEmpty()){
                errors.rejectValue("username", "invalid.registration.username.null");
            }
            if(!username.isEmpty() && !merchantDao.userNameAvailableNoSession(username)){
                errors.rejectValue("username", "invalid.registration.username.alreadyexists");
            }

            if(password.isEmpty() || !password.equals(password2)){
                errors.rejectValue("password", "invalid.password.match");
            }

            if(!email.matches("^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$")){
                errors.rejectValue("email","invalid.register.email");
            }

            if(!validateCaptcha(req)){
                errors.rejectValue("captchaResponse", "incorrect.captcha");
            }
            
            return !errors.hasErrors();
        } catch(Exception e){
            System.out.println(e.toString());
            for(StackTraceElement element : e.getStackTrace()){
                System.out.println(element.toString());
            }
            return false;
        }
       
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaptchaChallenge() {
        return captchaChallenge;
    }

    public void setCaptchaChallenge(String captchaChallenge) {
        this.captchaChallenge = captchaChallenge;
    }

    public String getCaptchaResponse() {
        return captchaResponse;
    }

    public void setCaptchaResponse(String captchResponse) {
        this.captchaResponse = captchResponse;
    }
}
