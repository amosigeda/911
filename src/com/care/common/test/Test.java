package com.care.common.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.mina.core.session.IoSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.app.MessageManager;
import com.care.common.config.ServiceBean;
import com.care.common.lang.Constant;
import com.care.sys.appinterfaces.AddDeviceFamilyAction;
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

import net.sf.json.JSONObject;


public class Test {
	
	
	public static void test() {
		
		JSONObject json1 = new JSONObject();
		json1.put("cc", "dd");
			
	}
	public static void main(String[] args) {
		
	}

}
