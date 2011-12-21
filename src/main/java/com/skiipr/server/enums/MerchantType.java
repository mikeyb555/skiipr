/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.enums;

/**
 *
 * @author Michael
 */
public enum MerchantType {
    BAR(0),
    NIGHTCLUB(1),
    RESTAURANT(2),
    TAKEAWAY(3),
    PUB(4),
    CAFE(5),
    STORE(6),
    SERVICE(7),
    OTHER(8);
    
    private int index;
    
    private MerchantType(int index){
        this.index = index;
        
    }
    
    public int getIndex(){
        return index;
    }
    
    public String getName(){
        return this.toString();
    }
    
}
