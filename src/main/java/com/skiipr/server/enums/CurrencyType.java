/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.enums;

/**
 *
 * @author Michael
 */
public enum CurrencyType {
    AUSTRALIAN_DOLLAR("AUD", "Australian Dollar"),
    CANADIAN_DOLLAR("CAD", "Canadian Dollar"),
    EURO("EUR", "Euro"),
    BRITISH_POUND("GBP", "British Pound"),
    JAPANESE_YEN("JPY", "Japanese Yen"),	
    US_DOLLAR("USD", "US Dollar"),
    NZ_DOLLAR("NZD", "New Zealand Dollar"),
    SWISS_FRANC("CHF", "Swiss Franc"),
    HK_DOLLAR("HKD", "Hong Kong Dollar"),
    SINGAPORE_DOLLAR("SGD", "Singapore Dollar"),
    SWEDISH_KRONA("SEK", "Swedish Krona"),
    DANISH_KRONE("DKK", "Danish Krone"),
    POLISH_ZLOTY("PLN", "Polish Zloty"),
    NORWEGIAN_KRONE("NOK", "Norwegian Krone"),
    HUNGARIAN_FORINT("HUF", "Hungarian Forint"),
    CZECH_KORUNA("CZK", "Czech Koruna"),
    ISRAEL_NEW_SHEKEL("ILS", "Israel New Shekel"),
    MEXICAN_PESO("MXN", "Mexican Peso"),
    PHILIPPINE_PESO("PHP", "Phillipine Peso"),
    NEW_TAIWAN_DOLLAR("TWD", "New Taiwan Dollar"),
    THAI_BAHT("THB", "Thai Baht");
    
    
    
    
    private String currencyCode;
    private String currencyName;
    
    private CurrencyType(String currencyCode, String currencyName){
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
    }
    
    public String getCurrencyCode(){
        return currencyCode;
    }
    
    public String getCurrencyName(){
        return currencyName;
    }
    
    
}
