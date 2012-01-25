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
import java.util.Collections;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

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
            return Collections.EMPTY_LIST;
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

    @Override
    public List<Category> findRange(Integer first, Integer max) {
        Long merchantID = sessionUser.getUser().getMerchantId();
        Criteria criteria = getSession().createCriteria(Category.class)
                .setMaxResults(max)
                .setFirstResult(first)
                .add(Restrictions.eq("merchantID", merchantID))
                .addOrder(Order.desc("categoryID"));
        List<Category> categories = criteria.list();
        if(categories.isEmpty()){
            return null;
        }
	return categories;
    }

    @Override
    public Integer countByMerchant() {
        Long merchantID = sessionUser.getUser().getMerchantId();
        Long count = (Long) getHibernateTemplate().find("SELECT count(*) FROM Category WHERE merchantID = ?", merchantID).get(0);
        return count.intValue();
    }
    
    @Override
    public boolean nameInUse(String name, Long existingCategoryID){
        Long merchantID = sessionUser.getUser().getMerchantId();
        Criteria criteria = getSession().createCriteria(Category.class)
                .add(Restrictions.eq("merchantID", merchantID))
                .add(Restrictions.eq("name", name))
                .add(Restrictions.ne("categoryID", existingCategoryID));
        return !criteria.list().isEmpty();
    }
}
