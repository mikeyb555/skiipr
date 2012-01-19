/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

/**
 *
 * @author Michael
 */
public class Banned {
    private Long bannedID;
    private String identifier;
    private Long merchantID;

    /**
     * @return the bannedID
     */
    public Long getBannedID() {
        return bannedID;
    }

    /**
     * @param bannedID the bannedID to set
     */
    public void setBannedID(Long bannedID) {
        this.bannedID = bannedID;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the merchantID
     */
    public Long getMerchantID() {
        return merchantID;
    }

    /**
     * @param merchantID the merchantID to set
     */
    public void setMerchantID(Long merchantID) {
        this.merchantID = merchantID;
    }
    
    
}
