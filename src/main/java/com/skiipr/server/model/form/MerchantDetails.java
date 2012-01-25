package com.skiipr.server.model.form;

import com.skiipr.server.model.DAO.MerchantDao;
import com.skiipr.server.model.Merchant;
import org.springframework.validation.Errors;

public class MerchantDetails {
    private String name;
    private String suburb;
    private String addressNumberStreet;
    private String addressPostcode;
    private String addressState;
    private String addressCountry;
    private String latitude;
    private String longitude;
    private int type;
    private String website;
    private String phoneNumber;
    private Boolean paypalEnabled;
    private Boolean codEnabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getAddressNumberStreet() {
        return addressNumberStreet;
    }

    public void setAddressNumberStreet(String addressNumberStreet) {
        this.addressNumberStreet = addressNumberStreet;
    }

    public String getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setAttributes(Merchant merchant){
        merchant.setName(name);
        merchant.setSuburb(suburb);
        merchant.setAddressNumberStreet(addressNumberStreet);
        merchant.setAddressPostcode(addressPostcode);
        merchant.setAddressState(addressState);
        merchant.setAddressCountry(addressCountry);
        merchant.setLatitude(latitude);
        merchant.setLongitude(longitude);
        merchant.setType(type);
        merchant.setWebsite(website);
        merchant.setPhoneNumber(phoneNumber);
        merchant.setPaypalEnabled(paypalEnabled);
        merchant.setCodEnabled(codEnabled);
    }
    
    public void getAttributes(Merchant merchant){
        name = merchant.getName();
        suburb = merchant.getSuburb();
        addressNumberStreet = merchant.getAddressNumberStreet();
        addressPostcode = merchant.getAddressPostcode();
        addressState = merchant.getAddressState();
        addressCountry = merchant.getAddressCountry();
        latitude = merchant.getLatitude();
        longitude = merchant.getLongitude();
        type = merchant.getType();
        website = merchant.getWebsite();
        phoneNumber = merchant.getPhoneNumber();
        paypalEnabled = merchant.getPaypalEnabled();
        codEnabled = merchant.getCodEnabled();
    }
    
    public boolean validate(Errors errors, MerchantDao dao){
        if((!this.getAddressPostcode().matches("^[1-9]{1}[0-9]{3}$"))){
            errors.rejectValue("addressPostcode", "invalid.postcode.size");
        }
        if(this.getWebsite().contains("http")){
            errors.rejectValue("website", "invalid.website.address");
        }
        if((this.getPhoneNumber().length() < 8) || (this.getPhoneNumber().length() > 20)){
            errors.rejectValue("phoneNumber", "invalid.phonenumber.size");
        }
        if(!dao.tradingNameAvailable(name)){
            errors.rejectValue("name", "invalid.name.taken");
        }
        return !errors.hasErrors();
    }

    /**
     * @return the paypalEnabled
     */
    public Boolean getPaypalEnabled() {
        return paypalEnabled;
    }

    /**
     * @param paypalEnabled the paypalEnabled to set
     */
    public void setPaypalEnabled(Boolean paypalEnabled) {
        this.paypalEnabled = paypalEnabled;
    }

    /**
     * @return the codEnabled
     */
    public Boolean getCodEnabled() {
        return codEnabled;
    }

    /**
     * @param codEnabled the codEnabled to set
     */
    public void setCodEnabled(Boolean codEnabled) {
        this.codEnabled = codEnabled;
    }
}
