/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.validators;

import com.skiipr.server.model.Order;
import java.util.Set;
import javax.validation.ConstraintViolation;

import javax.validation.metadata.BeanDescriptor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Michael
 */
@Component
public class OrderValidator implements Validator{
    @Override
        public boolean supports(Class<?> type) {
            return Order.class.equals(type);
        }

        @Override
        public void validate(Object o, Errors errors) {
            Order order = (Order) o;
            
            




        }

}
