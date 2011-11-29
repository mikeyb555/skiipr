/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO;

import com.skiipr.server.model.Product;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface ProductDao {
    public void save(Product product);
    public void update(Product product);
    public void delete(Product product);
    public Product findByID(Long id);
    public List<Product> findAll();
    public List<Product> findByCategoryID(Long id);
}
