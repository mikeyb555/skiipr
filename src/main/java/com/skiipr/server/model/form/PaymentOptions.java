/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.form;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.Plan;
import org.springframework.validation.Errors;

public class PaymentOptions {
    private Boolean paypalEnabled;
    private Boolean codEnabled;
    private Plan plan;
    private String paypal;

    public Boolean getPaypalEnabled() {
        return paypalEnabled;
    }

    public void setPaypalEnabled(Boolean paypalEnabled) {
        this.paypalEnabled = paypalEnabled;
    }

    public Boolean getCodEnabled() {
        return codEnabled;
    }

    public void setCodEnabled(Boolean codEnabled) {
        this.codEnabled = codEnabled;
    }
    
    public void setAttributes (Merchant merchant, Plan plan){
        
        if(plan.getCanCOD()){
            merchant.setCodEnabled(codEnabled);
        }
        
        if(plan.getCanPaypal()){    
            merchant.setPaypalEnabled(paypalEnabled);
            merchant.setPaypal(paypal);
        }

    }
    
    public void getAttributes (Merchant merchant){
        paypalEnabled = merchant.getPaypalEnabled();
        codEnabled = merchant.getCodEnabled();
        plan = merchant.getPlan();
        paypal = merchant.getPaypal();
    }
    
    public boolean validate(Errors errors, MerchantDao dao){
        return true;
        
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getPaypal() {
        return paypal;
    }

    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }
    
}
