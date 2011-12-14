/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO;

import com.skiipr.server.model.Category;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface CategoryDao {
    public void save(Category category);
    public void update(Category category);
    public void delete(Category category);
    public Category findByID(Long id);
    public List<Category> findAll();
    public List<Category> findByMerchantId();
    public List<Category> findByMerchantId(Long id);
    public Category findCategoryByMerchantId(Long categoryID);
    public Category findCategoryByMerchantId(Long categoryID, Long merchantID);
    
    
    
}
