/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.components.SessionUser;
import com.skiipr.server.model.Banned;
import com.skiipr.server.model.DAO.BannedDao;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository("BannedDao")
public class BannedDaoImpl extends HibernateDaoSupport implements BannedDao {
    
    @Autowired
    private SessionUser sessionUser;
    
    @Autowired
    public void init(SessionFactory factory){
        setSessionFactory(factory);
    }

    @Override
    public void save(Banned banned) {
        getHibernateTemplate().save(banned);
    }

    @Override
    public void update(Banned banned) {
        getHibernateTemplate().update(banned);
    }

    @Override
    public void delete(Banned banned) {
        getHibernateTemplate().delete(banned);
    }

    @Override
    public boolean isBanned(ArrayList<String> identifier, Long merchantID) {
        Criteria criteria = getSession().createCriteria(Banned.class)
                .add(Restrictions.in("identifier", identifier))
                .add(Restrictions.or(Restrictions.eq("merchantID", merchantID), Restrictions.eq("merchantID", 0l)));
        
        List<Banned> banned = criteria.list();
        return !banned.isEmpty();
                
    }
    
    @Override
    public List<Banned> findAll(){
        Long id = sessionUser.getUser().getMerchantId();
        Criteria criteria = getSession().createCriteria(Banned.class)
                .add(Restrictions.eq("merchantID", id));
        return criteria.list();
    }
    
    @Override
    public Banned getBan(Long id){
        Long merchantID = sessionUser.getUser().getMerchantId();
        Criteria criteria = getSession().createCriteria(Banned.class)
                .add(Restrictions.eq("bannedID", id))
                .add(Restrictions.eq("merchantID", merchantID));
        List<Banned> results = criteria.list();
        if(results.isEmpty()){
            return null;
        }else{
            return results.get(0);
        }
    }
    
}
