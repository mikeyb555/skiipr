/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.validators;

import com.skiipr.server.model.Order;
import com.skiipr.server.model.OrderProduct;
import java.math.BigDecimal;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderValidator implements Validator{
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
            
        }
}
