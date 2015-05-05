package com.unitop.sysmgr.bo.sys;

import java.io.Serializable;

public class UnionPingzcs implements Serializable {
	
	private static final long serialVersionUID = 7032154534987481635L;
	private String xitbs;
	private String pingzbs;
	
	public String getXitbs() {
		return xitbs;
	}
	public void setXitbs(String xitbs) {
		this.xitbs = xitbs;
	}
	public String getPingzbs() {
		return pingzbs;
	}
	public void setPingzbs(String pingzbs) {
		this.pingzbs = pingzbs;
	}
	
	@Override 
    public boolean equals(Object obj) { 
        if(obj instanceof UnionPingzcs){ 
        	UnionPingzcs pk=(UnionPingzcs)obj; 
            if(this.xitbs.equals(pk.xitbs)&&this.pingzbs.equals(pk.pingzbs))
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
}
