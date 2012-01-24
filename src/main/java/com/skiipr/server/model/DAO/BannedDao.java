package com.skiipr.server.model.DAO;

import com.skiipr.server.model.Banned;
import java.util.ArrayList;
import java.util.List;

public interface BannedDao {
    
    public boolean isBanned(ArrayList<String> identifier, Long merchantID);
    public void save(Banned banned);
    public void update(Banned banned);
    public void delete(Banned banned);
    public Banned getBan(Long id);
    public List<Banned> findAll();
    public boolean isBanned(String identifier);
}
