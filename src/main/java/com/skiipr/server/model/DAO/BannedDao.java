/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model.DAO;

import com.skiipr.server.model.Banned;
import java.util.ArrayList;

/**
 *
 * @author Michael
 */
public interface BannedDao {
    
    public boolean isBanned(ArrayList<String> identifier, Long merchantID);
    public void save(Banned banned);
    public void update(Banned banned);
    public void delete(Banned banned);
    
    
}
