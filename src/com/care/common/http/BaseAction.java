package com.care.common.http;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;

import com.care.common.config.ServiceBean;
import com.care.common.lang.Constant;
import com.care.sys.funcinfo.domain.FuncInfo;
import com.care.sys.monitorinfo.domain.MonitorInfo;
import com.care.sys.msginfo.domain.MsgInfo;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DateFormatTools;
import com.godoing.rose.lang.SystemException;

public abstract class BaseAction extends DispatchAction {

	public int result = Constant.FAIL_CODE;  //Ĭ��ʧ��
    public void setServlet(ActionServlet actionServlet) {

        /*���������ҳ���date�����Զ�ת��*/
        ConvertUtils.register(new SqlDateConverter(null), Date.class);
//        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
//        ConvertUtils.register(new BigIntegerConverter(null), BigInteger.class);
//        ConvertUtils.register(new FloatConverter(null), Float.class);
//        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
//        ConvertUtils.register(new ShortConverter(null), Short.class);
//        ConvertUtils.register(new SqlDateConverter(null), Date.class);
//        ConvertUtils.register(new SqlTimeConverter(null), Time.class);
//        ConvertUtils.register(new SqlTimestampConverter(null), Timestamp.class);
//        runThreads();
    }

    /*���м̳���ִ�з���ʱ���ᾭ��˷���*/
    protected ActionForward dispatchMethod(ActionMapping mapping,
                                           ActionForm form,
                                           HttpServletRequest request,
                                           HttpServletResponse response,
                                           String name) throws Exception {
//        Date dt = DateFormatTools.getDateByStr("2014-07-10 22:00:00");
//        if(System.currentTimeMillis() > dt.getTime()){
//            Result result = new Result();
//            result.setBackPage(Config.INDEX_PAGE);
//            result.setResultCode("timeout");
//            result.setResultType("fail");
//            request.setAttribute("result", null);
//            return mapping.findForward("result");
//        }
//        if(isTimeOut()){
//            LoginUser user = (LoginUser) request.getSession().getAttribute(
//                Config.SystemConfig.LOGINUSER);
//            if (user == null) { /*�ж��Ƿ�ʱ*/
//                Result result = new Result();
//                result.setBackPage(Config.INDEX_PAGE);
//                result.setResultCode("timeout");
//                result.setResultType("fail");
//                request.setAttribute("result", null);
//                return mapping.findForward("result");
//            }
//        }
//        this.myUserName = user.getUserName();
//        this.myUserCode = user.getUserCode();
//        this.myUserId = user.getUserId();
//        String lgs = getLogs();
//        if(lgs == null) lgs = "ϵͳ����";
//        if(name != null){
//            if(name.indexOf("query") > -1)lgs = lgs + "(��ѯ����)";
//            if(name.indexOf("insert") > -1)lgs = lgs + "(��������)";
//            if(name.indexOf("update") > -1)lgs = lgs + "(���²���)";
//            if(name.indexOf("delete") > -1)lgs = lgs + "(ɾ�����)";
//        }
//        LogsInfo l = new LogsInfo();
//        l.setByDate(new Date());
//        l.setUserId(this.myUserId);
//        l.setUserName(this.myUserName);
//        l.setDescs(lgs);
//        getLogsInfoFacade().insertLogsInfo(l);
//        response.setContentType("text/html; charset=gb2312");
//        request.setCharacterEncoding("gb2312");
//        response.setContentType("text/html; charset=UTF8");
//        request.setCharacterEncoding("UTF8");
        return super.dispatchMethod(mapping, form, request, response, name);
    }

    //0表示访问开关,1表示监听开关
    private String getSwitchTag(int type) throws SystemException{
    	List<DataMap> swi = ServiceBean.getInstance().getMonitorInfoFacade().getSwitchInfo(new MonitorInfo());
    	String tag = "visit_s";   //默认值
    	switch (type) {
		case 0:
			tag = "visit_s";
			break;
		case 1:
			tag = "device_s";
			break;
        case 2:
        	tag = "moni_s";
			break;
		default:
			break;
		}
    	return ""+swi.get(0).getAt(tag);
    }
    public void insertVisit(String href,String belongProject,String user_id,int type,Date start,Date end,int len) throws SystemException{
    	String tags = getSwitchTag(type);
    	
    	if(tags.equals("1")){  //1表示开启
    		String function = "";
    		FuncInfo vo = new FuncInfo();
    		vo.setCondition("funcDo = '" + href + "'");
    		List<DataMap> list = ServiceBean.getInstance().getFuncInfoFacade().getFuncInfo(vo);
    		if(list.size() > 0){
    			function = (String)list.get(0).getAt("funcName");
    		}
    		
    		MonitorInfo monitorInfo = new MonitorInfo();
    		monitorInfo.setStartTime(new Date());
    		Long startTime = start.getTime();
			Long endTime = end.getTime();
			Long between = endTime - startTime;
			int costTime = between.intValue();
			
    		monitorInfo.setFunctionHref(href);
    		monitorInfo.setBelongProject(belongProject);
    		monitorInfo.setPhone(user_id);
    		monitorInfo.setFunction(function);
    		monitorInfo.setStartTime(start);
    		monitorInfo.setEndTime(end);
    		monitorInfo.setCostTime(costTime);
    		monitorInfo.setType(String.valueOf(type));
    		monitorInfo.setLen(len);
    		try {
    			ServiceBean.getInstance().getMonitorInfoFacade().insertVisitInfo(monitorInfo);
    		} catch (SystemException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}   //工具
    	}
    	
    }
    public void getMessageFrom(JSONObject json,String user_id,String belongProject){
    	try{
    		MsgInfo msgInfo = new MsgInfo();
        	JSONArray msgData = new JSONArray();
        	
    		String condition = Constant.getDaysAgoCondition("msg_handler_date", -7);
    		msgInfo.setCondition("to_id ='"+user_id+"' and belong_project='"+belongProject+"' and is_handler ='0' and "+condition);
    		msgInfo.setOrderBy("id");
    		msgInfo.setSort("1");   //��id����
    		msgInfo.setFrom(0);
    		msgInfo.setPageSize(100);
    		List<DataMap> msgList = ServiceBean.getInstance().getMsgInfoFacade().getMsgInfo(msgInfo);
    		int msgCount = msgList.size();
    		for(int j=0;j<msgCount;j++){
    			JSONObject msgJson = new JSONObject();
    			String msg_id = ""+ msgList.get(j).getAt("id");
    			String msg_content = ""+ msgList.get(j).getAt("msg_content");
    			String msg_date = "" + msgList.get(j).getAt("msg_handler_date");
    			
    			msgJson.put("msg_id", msg_id);
    			msgJson.put("msg_content", msg_content);
    			msgJson.put("msg_date", msg_date);
    			
    			msgData.add(j,msgJson);
    		}
    		
    		json.put("msg_count", msgCount);  //��Ϣ����
    		json.put("msg_array", msgData); 
    	}catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public String getLogs(){return null;}

    protected String myUserName;
    protected String myUserCode;
    protected Integer myUserId;

    
    protected boolean isTimeOut(){
        return true;
    }

}
