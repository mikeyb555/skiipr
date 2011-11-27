/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO;

import com.skiipr.server.model.Order;
import java.util.List;

/**
 *
 * @author Michael
 */

public interface OrderDao {
    public void save(Order order);
    public void update(Order order);
    public void delete(Order order);
    public Order findByID(Long id);
    public List<Order> findByAll();
    
    
}
