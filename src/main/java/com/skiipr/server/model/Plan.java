package com.skiipr.server.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_plan")
public class Plan implements Serializable {
    
    @Id
    private Long planId;
    private String planName;
    private boolean canPaypal;
    private boolean canCOD;
    
    public void setPlanId(Long id){
        planId = id;
    }
    
    public void setPlanName(String planName){
        this.planName = planName;
    }
    
    public void setCanPaypal(boolean value){
        canPaypal = value;
    }
    
    public Long getPlanId(){
        return planId;
    }
    
    public String getPlanName(){
        return planName;
    }
    
    public boolean getCanPaypal(){
        return canPaypal;
    }

    /**
     * @return the canCOD
     */
    public boolean getCanCOD() {
        return canCOD;
    }

    /**
     * @param canCOD the canCOD to set
     */
    public void setCanCOD(boolean canCOD) {
        this.canCOD = canCOD;
    }
    
}
