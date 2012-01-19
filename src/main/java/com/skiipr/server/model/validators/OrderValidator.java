/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.validators;

import com.skiipr.server.enums.PaymentType;
import com.skiipr.server.model.DAO.BannedDao;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Order;
import com.skiipr.server.model.OrderProduct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderValidator implements Validator{
    
    @Autowired
    private BannedDao bannedDao;
    
    @Autowired
    private MerchantDao merchantDao;
    
    
    
    @Override
        public boolean supports(Class<?> type) {
            return Order.class.equals(type);
        }

        @Override
        public void validate(Object o, Errors errors) {
            Order order = (Order) o;
            if(order.getTotal() <= 0.00){
                errors.rejectValue("total", null);
            }
            float expectedTotal = 0;
            Set<OrderProduct> orderProducts = order.getOrderProducts();
            for (OrderProduct op: orderProducts){
                float amount = (op.getQuantity() * op.getProduct().getPrice().floatValue());
                expectedTotal += amount;
                
            }
            
            if (order.getTotal() != expectedTotal){
                errors.rejectValue("total", null);
            }
            
            if (!order.getEmail().matches("^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$")){
                errors.rejectValue("email", null);
            }
            ArrayList<String> identifiers = new ArrayList();
            identifiers.add(order.getDeviceID());
            identifiers.add(order.getEmail());
            if(bannedDao.isBanned(identifiers, order.getMerchantID())){
                errors.rejectValue("email", null);
                errors.rejectValue("deviceID", null);
            }
            
            if(!order.getMerchant().getOpen()){
                errors.rejectValue("merchantID", null);
            }
            
            if(order.getPaymentType().equals(PaymentType.PAYPAL) && !(order.getMerchant().getPlan().getCanPaypal())){
                
                errors.rejectValue("merchantID", null);
            
                
            }
            
            if(order.getPaymentType().equals(PaymentType.COD) && !(order.getMerchant().getPlan().getCanCOD())){
                errors.rejectValue("merchantID", null);
            }
            
            
            
            
            
            
        }
}
