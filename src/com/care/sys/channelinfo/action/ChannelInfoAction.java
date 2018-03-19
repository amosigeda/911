package com.care.sys.channelinfo.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.Config;
import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.CommUtils;
import com.care.sys.channelinfo.domain.ChannelInfo;
import com.care.sys.channelinfo.domain.logic.ChannelInfoFacade;
import com.care.sys.channelinfo.form.ChannelInfoForm;
import com.godoing.rose.http.common.HttpTools;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

public class ChannelInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(ChannelInfoAction.class);
	
	public ActionForward queryChannelInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String href= request.getServletPath();
		Date start = new Date();
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		ChannelInfoFacade info = ServiceBean.getInstance().getChannelInfoFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			ChannelInfoForm form = (ChannelInfoForm) actionForm;
			ChannelInfo vo = new ChannelInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");		
			
			/*���û������ֶ�*/
            form.setOrderBy("add_time"); 
            form.setSort("1");           
			if(startTime != null && !"".equals(startTime)){
				sb.append("substring(add_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(add_time,1,10) <= '"+endTime+"'");
			}
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		   
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getChannelInfoListByVo(vo);
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
			 
		} catch (Exception e) { 
			e.printStackTrace();
			logger.error(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); /* ����Ϊ����ҳ�棬���Գ������ת��ϵͳĬ��ҳ�� */
			if (e instanceof SystemException) { /* ����֪�쳣���н��� */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* ��δ֪�쳣���н�������ȫ�������δ֪�쳣 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		} finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href);
		return mapping.findForward("queryChannelInfo");
	}	
	
	public ActionForward initInsert(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("addChannelInfo");
	}
	
	public ActionForward insertChannelInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		try {
			ChannelInfoForm form = (ChannelInfoForm) actionForm;  //���ύ�ı?��װ���û���form��
			ChannelInfoFacade facade = ServiceBean.getInstance().getChannelInfoFacade();
			ChannelInfo vo = new ChannelInfo();
			int num = ServiceBean.getInstance().getChannelInfoFacade().getChannelInfoMaxId(vo) + 1;
			BeanUtils.copyProperties(vo, form);    //�ѱ?��Ϣ���Ƶ��û���Ϣ��
			vo.setId(num);
			vo.setChannelNo(form.getChannelNo() + num);									
			vo.setAddTime(new Date());
			vo.setStatus("");
			facade.insertChannelInfo(vo);
			
			
			result.setBackPage(HttpTools.httpServletPath(request,  //����ɹ�����ת��ԭ��ҳ��
					"queryChannelInfo"));
			result.setResultCode("inserts");    //���ò���Code
			result.setResultType("success");    //���ò���ɹ�
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"initInsert"));
			if (e instanceof SystemException) { /* ����֪�쳣���н��� */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* ��δ֪�쳣���н�������ȫ�������δ֪�쳣 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		} finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}
}
