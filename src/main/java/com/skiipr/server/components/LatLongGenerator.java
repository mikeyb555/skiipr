/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.components;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;

/**
 *
 * @author Michael
 */
public class LatLongGenerator {
    
    private String latitude;
    private String longitude;
    
    public LatLongGenerator(String address){
        System.out.println(address);
        try {
                        URL mapsUrl = new URL(
                                        "http://maps.googleapis.com/maps/api/geocode/xml?address="+ address + "&sensor=false");
                        InputStream openStream = mapsUrl.openStream();
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        DocumentBuilder db = dbf.newDocumentBuilder();
                        Document doc = db.parse(openStream);
                        NodeList formattedLatitude = doc.getElementsByTagName("lat");
                        
                        Element formattedLatitudeElement = (Element) formattedLatitude.item(0);
                        this.latitude=  formattedLatitudeElement.getTextContent();
                        doc.normalizeDocument();
                        NodeList formattedLongitude = doc.getElementsByTagName("lng");
                        Element formattedLongitudeElement = (Element) formattedLongitude.item(0);
                        this.longitude=  formattedLongitudeElement.getTextContent();
                        
                        
                        
                        
                        
                        
                } catch (Exception e) {
                        this.latitude = null;
                        this.latitude = null;
                        
                }
       
        
        
        
    
}
    public String getLatitude(){
        return this.latitude;
    }
    
    public String getLongitude(){
        return this.longitude;
    }
    
}
