package com.unitop.sysmgr.bo.sys;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class UnionJiedcs implements Serializable{
	
	private String jiedbs = "";
	private String cansbs = "";
	
	public String getJiedbs() {
		return jiedbs;
	}
	public void setJiedbs(String jiedbs) {
		this.jiedbs = jiedbs;
	}
	public String getCansbs() {
		return cansbs;
	}
	public void setCansbs(String cansbs) {
		this.cansbs = cansbs;
	}
	@Override 
    public boolean equals(Object obj) { 
        if(obj instanceof UnionJiedcs){ 
        	UnionJiedcs pk=(UnionJiedcs)obj; 
            if(this.jiedbs.equals(pk.jiedbs)&&this.cansbs.equals(pk.cansbs))
            { 
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
		return new ToStringBuilder(this).append("jiedbs", getJiedbs()).toString();
	}
	
}
