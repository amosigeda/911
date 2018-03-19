package com.care.sys.rolefuncinfo.domain;

import java.io.Serializable;
import com.godoing.rose.http.PublicVoBean;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class RoleFuncInfo extends PublicVoBean implements Serializable {

	private static final long serialVersionUID = -4777496506129465885L;
	
	public RoleFuncInfo(){
	}

	private String roleCode;
	private String funcCode;
	private String userCode;
	
	public void setUserCode(String userCode){
		this.userCode = userCode;
	}
	public String getUserCode(){
		return this.userCode;
	}
	public void setRoleCode(String roleCode){
		this.roleCode = roleCode;
	}
	public String getRoleCode(){
		return this.roleCode;
	}

	public void setFuncCode(String funcCode){
		this.funcCode = funcCode;
	}
	public String getFuncCode(){
		return this.funcCode;
	}
}
