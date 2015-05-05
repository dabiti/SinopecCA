package com.unitop.sysmgr.bo;

import org.apache.commons.lang.builder.ToStringBuilder;

public class UnionBiaod implements java.io.Serializable { 
    
	private String gongnid = "";
	
	private String zidmc = "";
	
	
	public String getGongnid() {
		return gongnid;
	}

	public void setGongnid(String gongnid) {
		this.gongnid = gongnid;
	}

	public String getZidmc() {
		return zidmc;
	}

	public void setZidmc(String zidmc) {
		this.zidmc = zidmc;
	}

	@Override 
    public boolean equals(Object obj) { 
        if(obj instanceof UnionBiaod){ 
        	UnionBiaod pk=(UnionBiaod)obj; 
            if(this.gongnid.equals(pk.gongnid)&&this.zidmc.equals(pk.zidmc)){ 
                return true; 
            } 
        } 
        return false; 
    } 

    @Override 
    public int hashCode() { 
        return super.hashCode(); 
    }
    
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("gongnid", getGongnid()).toString();
	}
	
}
