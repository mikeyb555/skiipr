package com.skiipr.server.model.DAO;

import com.skiipr.server.model.Merchant;


public interface MerchantDao {
    public void save(Merchant merchant);
    public void update(Merchant merchant);
    public void delete(Merchant merchant);
    public Merchant findByUsername(String username);
    public Merchant findById(Long id);
}
