/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.components;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Michael
 */
public class LatLongGeneratorTest {
    
    @Test
    public void testAddress(){
        LatLongGenerator llg = new LatLongGenerator("8 Mountview St Aspley");
        System.out.println("Latitude is" + llg.getLatitude());
        System.out.println("Longitude is" + llg.getLongitude());
        Assert.assertEquals(llg.getLatitude(), "-27.3669920");
        Assert.assertEquals(llg.getLongitude(), "153.0118790");
        
    }
    
}
