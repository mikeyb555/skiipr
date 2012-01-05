/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.enums;

/**
 *
 * @author Michael
 */
public enum Country{
    
    AUSTRALIA("AUD", "Australia"),
    CANADA("CAD", "Canada"),
    AUSTRIA("AUS", "Austria"),
    BELGIUM("BEL", "Belgium"),
    CYPRUS("CYP","Cyprus"),
    ESTONIA("EST", "Estonia"),
    FINLAND("FIN", "Finland"),
    FRANCE("FRA", "France"),
    GERMANY("GER","Germany"),
    GREECE("GRE", "Greece"),
    IRELAND("IRE", "Ireland"),
    ITALY("ITA", "Italy"),
    LUXEMBOURG("LUX", "Luxembourg"),
    MALTA("MAL", "Malta"),
    NETHERLANDS("NET", "Netherlands"),
    PORTUGAL("POR", "Portugal"),
    SLOVAKIA("SLA","Slovakia"),
    SLOVENIA("SLE", "Slovenia"),
    SPAIN("SPA", "Spain"),
    BRITAIN("GBP", "Britain"),
    JAPAN("JPY", "Japan"),	
    UNITED_STATES("USD", "United States"),
    NEW_ZEALAND("NZD", "New Zealand"),
    SWITZERLAND("CHF","Switzerland"),
    HONG_KONG("HKD", "Hong Kong"),
    SINGAPORE("SGD", "Singapore"),
    SWEDEN("SEK", "Sweden"),
    DENMARK("DKK", "Denmark"),
    POLAND("PLN", "Poland"),
    NORWAY("NOK", "Norway"),
    HUNGARY("HUF", "Hungary"),
    CZECH_REPUBLIC("CZK", "Czech Republic"),
    ISRAEL("ILS", "Israel"),
    MEXICO("MXN", "Mexico"),
    PHILIPPINES("PHP", "Phillipinies"),
    TAIWAN("TWD", "Taiwan"),
    THAILAND("THB", "Thailand");
    
    private String countryCode;
    private String countryName;
    private Country(String countryCode, String countryName){
        this.countryCode = countryCode;
        this.countryName = countryName;
    }
    
    public String getCountryCode(){
        return countryCode;
    }
    
    public String getCountryName(){
        return countryName;
    }
    
    
    
}
