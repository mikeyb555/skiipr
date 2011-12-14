/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.components.SessionUser;
import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.LoginUser;
import com.skiipr.server.model.Merchant;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository("CategoryDao")
public class CategoryDaoImpl extends HibernateDaoSupport implements CategoryDao {
    
    @Autowired
    private MerchantDao merchantDao;
    
    @Autowired
    private SessionUser sessionUser;
    
    
    
    @Autowired
    public void init(SessionFactory factory){
        setSessionFactory(factory);
    }

    @Override
    public void save(Category category) {
        getHibernateTemplate().save(category);
    }

    @Override
    public void update(Category category) {
        getHibernateTemplate().update(category);
    }

    @Override
    public void delete(Category category) {
        getHibernateTemplate().delete(category);
    }

    @Override
    public Category findByID(Long id) {
        List list = getHibernateTemplate().find("from Category where categoryID=?", id);
        if(list.isEmpty()){
            return null;
        }
	return (Category)list.get(0);
    }

    @Override
    public List<Category> findAll() {
        List list = getHibernateTemplate().find("from Category");
	return (List<Category>) list;
    }
    
    @Override
    public List<Category> findByMerchantId(){
        LoginUser user = sessionUser.getUser();
        return findByMerchantId(user.getMerchantId());
    }
        
    
    
    @Override
    public List<Category> findByMerchantId(Long id){
        
        List list = getHibernateTemplate().find("from Category where merchantID =?", id);
        if(list.isEmpty()){
            return null;
        }
	return (List<Category>) list;
        
    }
    
    @Override
    public Category findCategoryByMerchantId(Long categoryID){
       LoginUser user = sessionUser.getUser();
       return findCategoryByMerchantId(categoryID, user.getMerchantId());
    }
    
    @Override
    public Category findCategoryByMerchantId(Long categoryID, Long merchantID){
        String[] params = {"catID", "merchID"};
        Object[] values = {categoryID, merchantID};
        List<Category> categories = getHibernateTemplate().findByNamedParam("from Category where (categoryID= :catID) AND (merchantID = :merchID)", params, values);
        if(categories.isEmpty()){
            return null;
        }
	return (Category) categories.get(0);
         
         
        
       
    }
    
}
