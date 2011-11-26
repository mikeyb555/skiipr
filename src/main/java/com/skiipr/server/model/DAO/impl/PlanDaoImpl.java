package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.model.DAO.PlanDao;
import com.skiipr.server.model.Plan;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("PlanDao")
public class PlanDaoImpl extends HibernateDaoSupport implements PlanDao{

    @Autowired
    public void init(SessionFactory factory) {
        setSessionFactory(factory);
    }
    
    @Override
    public void save(Plan plan) {
        getHibernateTemplate().save(plan);
    }

    @Override
    public void update(Plan plan) {
        getHibernateTemplate().update(plan);
    }

    @Override
    public void delete(Plan plan) {
        getHibernateTemplate().delete(plan);
    }

    @Override
    public Plan findByID(Long id) {
        List list = getHibernateTemplate().find("from Plan where planId=?", id);
        if(list.isEmpty()){
            return null;
        }
	return (Plan)list.get(0);
    }

    @Override
    public List<Plan> findAll() {
        List list = getHibernateTemplate().find("from Plan");
        if(list.isEmpty()){
            return null;
        }
	return (List<Plan>) list;
    }

}