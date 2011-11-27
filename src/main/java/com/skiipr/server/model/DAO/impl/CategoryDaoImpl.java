/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.CategoryDao;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository("CategoryDao")
public class CategoryDaoImpl extends HibernateDaoSupport implements CategoryDao {
    
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
        List list = getHibernateTemplate().find("from Category where CategoryId=?", id);
        if(list.isEmpty()){
            return null;
        }
	return (Category)list.get(0);
    }

    @Override
    public List<Category> findAll() {
        List list = getHibernateTemplate().find("from Category");
        if(list.isEmpty()){
            return null;
        }
	return (List<Category>) list;
    }
    
}
