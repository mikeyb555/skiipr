package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("MerchantDao")
public class MerchantDaoImpl extends HibernateDaoSupport implements MerchantDao{

    @Autowired
    public void init(SessionFactory factory) {
        setSessionFactory(factory);
    }
    
    @Override
    public void save(Merchant merchant) {
        getHibernateTemplate().save(merchant);
    }

    @Override
    public void update(Merchant merchant) {
        getHibernateTemplate().update(merchant);
    }

    @Override
    public void delete(Merchant merchant) {
        getHibernateTemplate().delete(merchant);
    }

    @Override
    public Merchant findByUsername(String username) {
        List list = getHibernateTemplate().find("from Merchant where username=?", username);
        if(list.isEmpty()){
            return null;
        }
	return (Merchant)list.get(0);
    }

    @Override
    public Merchant findById(Long id) {
        List list = getHibernateTemplate().find("from Merchant where merchantID=?", id);
        if(list.isEmpty()){
            return null;
        }
	return (Merchant)list.get(0);
    }

    @Override
    public List<Merchant> findAll() {
        return getHibernateTemplate().find("from Merchant");
    }
    
}
