package com.skiipr.server.model;

import com.skiipr.server.services.EmailService;
import java.io.Serializable;
import java.lang.String;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

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
    private Long lastChange;
    private boolean consoleSoundEnabled;
    private boolean locked;
    private String verCode;
    
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
    public Long getLastChange() {
        return lastChange;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    @JsonIgnore
    public boolean isConsoleSoundEnabled() {
        return consoleSoundEnabled;
    }

    public void setConsoleSoundEnabled(boolean consoleSoundEnabled) {
        this.consoleSoundEnabled = consoleSoundEnabled;
    }
    
    @JsonIgnore
    public HashMap getConsoleOptions(){
        HashMap map = new HashMap();
        map.put("consoleSoundEnabled", consoleSoundEnabled);
        return map;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }
    
    public void setNewPassword(String raw){
        PasswordEncoder passwordEncoder = new ShaPasswordEncoder();
        String saltSeed = UUID.randomUUID().toString();
        String genSalt = passwordEncoder.encodePassword(saltSeed, saltSeed);
        genSalt = genSalt.substring(10, 15);
        salt = genSalt;
        password = passwordEncoder.encodePassword(raw, salt);
    }
    
    public boolean activateEmail(String code){
        if(verCode.equals(code)){
            locked = false;
            return true;
        }
        return false;
    }
    
    public void prepareActivationEmail(){
        locked = true;
        PasswordEncoder passwordEncoder = new ShaPasswordEncoder();
        String saltSeed = UUID.randomUUID().toString();
        String genSalt = passwordEncoder.encodePassword(saltSeed, saltSeed);
        genSalt = genSalt.substring(20, 30);
        verCode = genSalt;
    }
    
    public void sendActivationEmail(){
        StringBuilder body = new StringBuilder();
        body.append("Welcome to skiipr, click the following link to verify your account ");
        body.append("http://skiipr.com/activate/");
        body.append(merchantID);
        body.append("/");
        body.append(verCode);
        LinkedList<String> recipients = new LinkedList<String>();
        recipients.add(email);
        EmailService.SendMail("noreply@skiipr.com", recipients, "Skiipr Activation Email ACTION REQUIRED", body.toString());
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }
}
