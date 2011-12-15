/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.components;

import com.skiipr.server.enums.Status;

/**
 *
 * @author Tim
 */
public class FlashNotification {
    
    
    private String message;
    
    private Status status;
    
    private FlashNotification(){
         
    }
    
    public static FlashNotification create(Status status, String message){
        FlashNotification notification = new FlashNotification();
        notification.message = message;
        notification.status = status;
        return notification;
    }
        
        
    
    
    public Status getStatus(){
        return status;
        
    }
    
    public String getMessage(){
        return message;
    }
    
    
}
