package com.skiipr.server.model.DAO.impl;

import com.skiipr.server.components.SessionUser;
import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.LoginUser;
import com.skiipr.server.model.Merchant;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("MerchantDao")
public class MerchantDaoImpl extends HibernateDaoSupport implements MerchantDao{

    @Autowired
    private SessionUser sessionUser;
    
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
    
    @Override
    public ArrayList<Merchant> findWithinRadius(double lat, double lon, double radius){
        List<Merchant> merchants = findAll();
        ArrayList<Merchant> returnList = new ArrayList();
        for(Merchant merchant: merchants){
            String stringLatitude = merchant.getLatitude();
            String stringLongitude = merchant.getLongitude();
            double merchantLatitude = Double.parseDouble(stringLatitude);
            double merchantLongitude = Double.parseDouble(stringLongitude);
            if (withinRadius(merchantLatitude, merchantLongitude, lat, lon, radius)){                
                returnList.add(merchant);
            } 
        }
        return returnList; 
    }
    
    private boolean withinRadius(double merchantLatitude, double merchantLongitude, double userLatitude, double userLongitude, double radius){
        double theta = userLongitude - merchantLongitude;
        double dist = Math.sin(deg2rad(userLatitude)) * Math.sin(deg2rad(merchantLatitude)) 
                + Math.cos(deg2rad(userLatitude)) * Math.cos(deg2rad(merchantLatitude)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return (radius >= dist);
    }
    
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public List<Merchant> findByName(String name) {
        getHibernateTemplate().setMaxResults(10);
        return getHibernateTemplate().find("from Merchant WHERE name LIKE concat('%',?,'%')", name);
    }
   
    @Override
    public boolean usernameAvailable(String username){
        LoginUser user = sessionUser.getUser();
        List<Merchant> results = getHibernateTemplate().find("FROM Merchant WHERE (username = ?) AND (merchantID != ?)", username, user.getMerchantId());
        return results.isEmpty();
    }   
    
    @Override
    public Merchant findCurrentMerchant(){
        return findById(sessionUser.getUser().getMerchantId());
    }
    
    @Override
    public boolean tradingNameAvailable(String name){
        LoginUser user = sessionUser.getUser();
        List<Merchant> results = getHibernateTemplate().find("FROM Merchant WHERE (name = ?) AND (merchantID != ?)", name, user.getMerchantId());
        return results.isEmpty();
    }

    @Override
    public Long getChangeToken() {
        Long merchantID = sessionUser.getUser().getMerchantId();
        getHibernateTemplate().find("SELECT lastChange FROM Merchant WHERE merchantID = ?", merchantID);
        List results = getHibernateTemplate().find("SELECT lastChange FROM Merchant WHERE merchantID = ?", merchantID);
        return (Long) results.get(0);
    }
    
}
