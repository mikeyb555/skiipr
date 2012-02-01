/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skiipr.server.model;

import java.io.Serializable;
import java.util.Set;
import java.util.regex.Pattern;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "tbl_merchant")
public class Merchant implements Serializable {
    private String username;
    private String password;
    private Long merchantID;
    private int loginCount;
    private int failedLoginTime;
    private int lastLoginTime;
    private String salt;
    private String name;
    private String suburb;
    private String addressNumberStreet;
    private String addressPostcode;
    private String addressState;
    private String addressCountry;
    private String paypal;
    private String latitude;
    private String longitude;
    //@Email
    private String email;
    private int planId;
    private int type;
    private Plan plan;
    private Set categories;
    private String currencyType;
    private String website;
    //@Length(max = 5)
    private String phoneNumber;
    private Boolean open;
    private Boolean paypalEnabled;
    private Boolean codEnabled;
    private int lastChange;
    
    @JsonIgnore
    public String getUsername(){
        return username;
    }
    
    @JsonIgnore
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    @Id
    public Long getMerchantID(){
        return merchantID;
    }
    
    public void setSalt(String salt){
        this.salt = salt;
    }
    
    public void setMerchantID(Long id){
        merchantID = id;
    }
    
    public void setLoginCount(int loginCount){
        this.loginCount = loginCount;
    }
    
    public void setFailedLoginTime(int time){
        failedLoginTime = time;
    }
    
    public void setLastLoginTime(int time){
        lastLoginTime = time;
    }
    
    @JsonIgnore
    public int getLoginCount(){
        return loginCount;
    }
    
    @JsonIgnore
    public int getFailedLoginTime(){
        return failedLoginTime;
    }
    
    @JsonIgnore
    public int getLastLoginTime(){
        return lastLoginTime;
    }
    
    @JsonIgnore
    public String getSalt(){
        return salt;
    }
    
    public void noteLoginFailure(){
        setFailedLoginTime((int) (System.currentTimeMillis() / 1000L));
        setLoginCount(getLoginCount() + 1);
    }
    
    public void noteLoginSuccess(){
        setLoginCount(0);
        setLastLoginTime((int) (System.currentTimeMillis() / 1000L));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String addressNumber) {
        this.suburb = addressNumber;
    }

    public String getAddressNumberStreet() {
        return addressNumberStreet;
    }

    public void setAddressNumberStreet(String addressStreet) {
        this.addressNumberStreet = addressStreet;
    }

    public String getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getPaypal() {
        return paypal;
    }

    public void setPaypal(String paypal) {
        this.paypal = paypal;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    @JsonIgnore
    @OneToOne
    public Plan getPlan(){
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    
    public void setCategories(Set categories){
        this.categories = categories;
    }
    
    public Set getCategories(){
        return categories;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
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

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getPaypalEnabled() {
        return paypalEnabled;
    }

    public void setPaypalEnabled(Boolean paypalEnabled) {
        this.paypalEnabled = paypalEnabled;
    }

    public Boolean getCodEnabled() {
        return codEnabled;
    }

    public void setCodEnabled(Boolean codEnabled) {
        this.codEnabled = codEnabled;
    }

    @JsonIgnore
    public int getLastChange() {
        return lastChange;
    }

    public void setLastChange(int lastChange) {
        this.lastChange = lastChange;
    }
    
}
