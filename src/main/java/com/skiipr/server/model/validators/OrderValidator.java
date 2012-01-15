/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.validators;

import com.skiipr.server.model.Order;
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
        }
}
