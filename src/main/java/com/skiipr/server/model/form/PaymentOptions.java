/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.form;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import com.skiipr.server.model.Plan;
import org.springframework.validation.Errors;

/**
 *
 * @author Michael
 */
public class PaymentOptions {
    private Boolean paypalEnabled;
    private Boolean codEnabled;
    private Plan plan;
    private String paypal;

    /**
     * @return the paypalEnabled
     */
    public Boolean getPaypalEnabled() {
        return paypalEnabled;
    }

    /**
     * @param paypalEnabled the paypalEnabled to set
     */
    public void setPaypalEnabled(Boolean paypalEnabled) {
        this.paypalEnabled = paypalEnabled;
    }

    /**
     * @return the codEnabled
     */
    public Boolean getCodEnabled() {
        return codEnabled;
    }

    /**
     * @param codEnabled the codEnabled to set
     */
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
        
        System.out.println(plan.getCanPaypal());
        
    }
    
    public boolean validate(Errors errors, MerchantDao dao){
        return true;
        
    }

    /**
     * @return the plan
     */
    public Plan getPlan() {
        return plan;
    }

    /**
     * @param plan the plan to set
     */
    public void setPlan(Plan plan) {
        this.plan = plan;
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
    
}
