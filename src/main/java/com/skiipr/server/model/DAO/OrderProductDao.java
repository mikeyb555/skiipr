/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO;

import com.skiipr.server.model.OrderProduct;
import com.skiipr.server.model.Product;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface OrderProductDao {
    public void save(OrderProduct orderProduct);
    public void update(OrderProduct orderProduct);
    public void delete(OrderProduct orderProduct);
    public List<OrderProduct> findByOrderID(Long id);
    
}
