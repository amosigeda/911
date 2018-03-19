package com.care.sys.rolefuncinfo.form;

import java.io.Serializable;
import com.godoing.rose.http.PublicFormBean;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class RoleFuncInfoForm extends PublicFormBean implements Serializable {

	private static final long serialVersionUID = -8516517719578651371L;
	public RoleFuncInfoForm(){
	}

	private String roleCode;
	private String funcCode;
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
