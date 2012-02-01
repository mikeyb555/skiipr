package com.skiipr.server.model.DAO;

import com.skiipr.server.model.Merchant;
import java.util.ArrayList;
import java.util.List;


public interface MerchantDao {
    public void save(Merchant merchant);
    public void update(Merchant merchant);
    public void delete(Merchant merchant);
    public Merchant findByUsername(String username);
    public Merchant findById(Long id);
    public List<Merchant> findAll();
    public ArrayList<Merchant> findWithinRadius(double lat, double lon, double radius);
    public List<Merchant> findByName(String name);
    public boolean usernameAvailable(String username);
    public boolean tradingNameAvailable(String name);
    public Merchant findCurrentMerchant();
    public int getChangeToken();
}
