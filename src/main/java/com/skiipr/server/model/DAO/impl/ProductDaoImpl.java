/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.model.Category;
import com.skiipr.server.model.DAO.ProductDao;
import com.skiipr.server.model.Product;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository("ProductDao")
public class ProductDaoImpl extends HibernateDaoSupport implements ProductDao {

    @Autowired
    public void init(SessionFactory factory) {
        setSessionFactory(factory);
    }
    
    @Override
    public void save(Product product) {
        getHibernateTemplate().save(product);
    }

    @Override
    public void update(Product product) {
        getHibernateTemplate().update(product);
    }

    @Override
    public void delete(Product product) {
        getHibernateTemplate().delete(product);
    }

    @Override
    public Product findByID(Long id) {
        Product product = (Product) getSession().get(Product.class, id);
	return product;
    }

    @Override
    public List<Product> findAll() {
        List list = getHibernateTemplate().find("from Product");
        if(list.isEmpty()){
            return null;
        }
	return (List<Product>) list;
    }
    
    @Override
    public List<Product> findByCategoryID(Long id){
        List list = getHibernateTemplate().find("from Product where category.categoryID=?", id);
        if(list.isEmpty()){
            return null;
        }
	return (List<Product>) list;   
    }

    @Override
    public Product findByIDNoRelation(Long id) {
        List products = getHibernateTemplate().find("from Product where productID=?", id);
        if(products.isEmpty()){
            return null;
        }
	return (Product) products.get(0);
    }
    
}