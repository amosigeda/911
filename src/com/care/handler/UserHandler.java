package com.care.handler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.care.app.AbstractHandler;
import com.care.app.MessageManager;
import com.care.common.config.ServiceBean;
import com.care.common.lang.Constant;
import com.care.sys.appuserinfo.domain.AppUserInfo;
import com.care.sys.appuserinfo.domain.logic.AppUserInfoFacade;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class UserHandler extends AbstractHandler {
	private static Logger logger = Logger.getLogger(UserHandler.class);
	JSONObject js = null;
	JSONObject message = new JSONObject();
	String loginout = "{\"request\":\"SERVER_LOGINOUT_RE\"}";
	int global_user_id;
	public UserHandler() {
		super();
	}
	public UserHandler(IoSession session, String request) {
		super(session, request);
		message = JSONObject.fromObject(request);
		js = new JSONObject();
		js.put("request", message.getString("request") + "_RE");
		js.put("resultCode", 0);
		js.put("timesend",
				message.has("timesend") ? message.getString("timesend") : null);
	}
	public void handle() {
		switch (ChatCommand.valueOf(message.getString("request")).toInt()) {
		case ChatCommand.MSG_SENDMSG:
			Chat();
			break;
		case ChatCommand.USER_LOGIN:
			Login();
			break;
		}
		UserSetSessionHandle(global_user_id,session);
	}
	public void Login() {
		String loginaccount = message.has("loginaccount") ? message
				.getString("loginaccount") : null;
				logger.info("loginaccount======="+loginaccount);
	    String passwd = message.has("passwd") ? message.getString("passwd") : null;
				AppUserInfo us = new AppUserInfo();
				us.setCondition("user_name ='"+loginaccount+"'");
				AppUserInfoFacade facade = ServiceBean.getInstance().getAppUserInfoFacade();
				try {
				List<DataMap> list = facade.getAppUserInfo(us);//����û����Ƿ�ע��
				if(list.size() > 0){
					us.setAge((String)list.get(0).getAt("age"));
					us.setUserName((String)list.get(0).getAt("user_name"));
					us.setId((Integer)list.get(0).getAt("id"));
					us.setUserHeight(String.valueOf(list.get(0).getAt("height")));
					us.setHead((String)list.get(0).getAt("head"));
					global_user_id = us.getId();
					boolean is_unique_id_equal = false;
					String ip1 = (String) session.getAttribute("ip");
					 logger.info("in login ip1 is ============="+ip1);
					if (MessageManager.playerSet.containsKey(us.getId())) {
						IoSession resession = MessageManager.playerSet.get(us.getId());
						resession.removeAttribute("user");
			            logger.info("in login before session equals resession ==========");
						if (!session.equals(resession)) {
							logger.info("in login resession is ================="+resession);
							resession.write(loginout);
							resession.close(true);
						}
					}
					session.setAttribute("user", us);
					MessageManager.playerSet.put(us.getId(), session);
					MessageManager.userSet.put(us.getId(), us);
				}
				}catch(Exception e) {
					e.printStackTrace();
				}

//		usi.AddLoginInfo(us.getId(), ip1, 1, 1);		
	}
	
	private void Chat() {
		logger.info("is in chat ====================================");
		Timestamp timesend = Tools.getTimestamp();
		JSONObject jsw = new JSONObject();
		jsw.put("request", "MSG_SENDMSG_RE");
		jsw.put("timest", timesend.toString());
		jsw.put("resultCode", 0);
		int userid = message.has("user_id") ? message.getInt("user_id") : 0;
		global_user_id = userid;
		int abkey = message.has("abkey") ? message.getInt("abkey") : 2;
		int device_id = message.has("device_id") ? message.getInt("device_id")
				: 2;
		String username = message.has("user_name") ? message
				.getString("user_name") : null;
		int type = message.has("type") ? message.getInt("type") : null;
		jsw.put("timesend", message.get("timesend"));
		String content_name = message.has("content_name") ? message
				.getString("content_name") : null;
		int chatgroupid = message.has("chatgroupid") ? message
				.getInt("chatgroupid") : 0;
		String imei = message.has("to_id") ? message.getString("to_id") : null;
		int to_id = 0;
		logger.info("in userhandler chat imei ==================="+imei);
		PhoneInfo pi = new PhoneInfo();
		pi.setCondition("serie_no = '"+imei+"'");
		PhoneInfoFacade pFacade = ServiceBean.getInstance().getPhoneInfoFacade();
		try {
			List<DataMap> pList = pFacade.getPhoneInfo(pi);
			if(pList.size() > 0) {
				to_id = (Integer)pList.get(0).getAt("id");
				logger.info("in userhandler chat to_id ==================="+to_id);
			}
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int send_type = message.has("send_type") ? message.getInt("send_type")
				: 0;
		int timelen = message.has("timelen") ? message.getInt("timelen") : 0;
		int c_type = message.has("c_type") ? message.getInt("c_type") : 0;
		// String c_type=message.has("c_type") ?
		// message.getString("c_type"):null;
		String content = null;
		String vio = null;
		byte[] b = null;
		if (c_type == VarTool.text) {
			content = message.has("content") ? message.getString("content")
					: null;
		} else {
			vio = message.has("content") ? message.getString("content") : null;
			try {
				b = Base64.decodeBase64(vio.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		message.remove("to_id");
		message.remove("MSG_SENDMSG");
		message.remove("user_id");
		message.put("request", "MSG_RECEIVEMSG");
		message.put("from_id", userid);
		message.put("from_type", VarTool.user_type);
		message.put("from_username", username);
		message.put("timesend", timesend.toString());
		String path = null;
		int is_receive = 0;
		if (c_type == VarTool.sound) {
			path = FileTool.getGroupVoicePath(chatgroupid) + '/'
					+ Tools.getTime() + ".amr";
			if (type == VarTool.chat_group) {
//				DeviceGroupEntity dge = usi.getDeviceGroupInfo(chatgroupid);
//				to_id = dge.getDevice_id();
			}

			FileOutputStream os;
			try {
				// os = new FileOutputStream(System.getProperty("user.dir") +
				// path);
				os = new FileOutputStream(MessageManager.system_path + path);
				os.write(b);
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			content = path;

		}
		long msg_id = 0;
		if (type == VarTool.chat_group) {
//			if (c_type != VarTool.photo)
//				csi.AddGroupMsg(chatgroupid, userid, VarTool.user_type,
//						content, c_type, timelen);
//			Broadcast(chatgroupid, userid);
//			jsw.put("resultCode",chatGroupReceive);
		} else if (send_type == VarTool.send_device) {
//			msg_id = csi.AddChatMsgRetId(userid, to_id, content, VarTool.sound,
//					timesend, is_receive, VarTool.user_type,
//					VarTool.device_type, timelen);
		}
		if (c_type == VarTool.sound) {
			logger.info("-----------------------------------------------is in Chat msg_id==="+msg_id);
//			Set<Integer> key = MessageManager.tzSet.keySet();
//			for(Iterator<Integer> it = key.iterator();it.hasNext();) {
//				int s = it.next();
//				logger.info("tzSet id is============="+s+":"+MessageManager.tzSet.get(s));
//			}
			Set<Integer> key = MessageManager.tzSet.keySet();
			for(Iterator<Integer> it = key.iterator();it.hasNext();) {
				int s = it.next();
				logger.info("tzSet id is============="+s+":"+MessageManager.tzSet.get(s));
			}
			IoSession to_session = MessageManager.tzSet.get(to_id);
			logger.info("-----------------------------------------------is in Chat to_session==="+to_session);
			if (to_session != null) {
				ToDevice(to_session, abkey, vio,msg_id);
				jsw.put("resultCode", 1);
			}else {
//				if(type == VarTool.chat_group) {
//					GroupMsg gm = csi.getGroupMsgByGroupUidDesc(chatgroupid, userid,
//							VarTool.user_type);
//					csi.AddDeviceGroupUnSend(gm.getId(), to_id,
//							VarTool.chat_group);
//				}
			}
		}
		session.write(jsw);
	}
	
	public static void ToDevice(IoSession sess, int i, String byt,long msg_id) {
		// TZ_DL_VOICE#00005401#1##
		if (sess == null || byt == null)
			return;
		StringBuffer sb = new StringBuffer("");

		int j = byt.length();
		int k = (j + "").length();
		String sblen = "00000000";
		switch (k) {
		case 1:
			sblen = "0000000" + j;
			break;
		case 2:
			sblen = "000000" + j;
			break;
		case 3:
			sblen = "00000" + j;
			break;
		case 4:
			sblen = "0000" + j;
			break;
		case 5:
			sblen = "000" + j;
			break;
		case 6:
			sblen = "00" + j;
			break;
		case 7:
			sblen = "0" + j;
			break;
		case 8:
			sblen = "" + j;
			break;
		default:
			break;
		}
		sb.insert(0, "TZ_DL_VOICE_RSP#" + sblen + "#" + i + "##");
		sb.append(byt).append("#");
		int msg_len = (msg_id + "").length();
		String msglen = "000000000";
		switch (msg_len) {
		case 1:
			msglen = "00000000" + msg_id;
			break;
		case 2:
			msglen = "0000000" + msg_id;
			break;
		case 3:
			msglen = "000000" + msg_id;
			break;
		case 4:
			msglen = "00000" + msg_id;
			break;
		case 5:
			msglen = "0000" + msg_id;
			break;
		case 6:
			msglen = "000" + msg_id;
			break;
		case 7:
			msglen = "00" + msg_id;
			break;
		case 8:
			msglen = "0" + msg_id;
			break;
		case 9:
			msglen =msg_id+"";
		default:
			break;
		}
		sb.append(msglen).append("#");
		sess.write(sb.toString());
	}
	private void UserSetSessionHandle(int id,IoSession isession) {
		logger.info("is in UserSetSessionHandle id is ======================="+id);		
		if(id != 0) {
		if (!MessageManager.playerSet.containsKey(id)) {
			logger.info("is in UserSetSessionHandle playerSet is not containskey==============="+id);
			logger.info("is in UserSetSessionHandle playerSet is not containskey session==============="+isession);
			MessageManager.playerSet.put(id, isession);
			AppUserInfo us = new AppUserInfo();
			us.setCondition("id = '"+id+"'");
			AppUserInfoFacade facade = ServiceBean.getInstance().getAppUserInfoFacade();
			try {
			List<DataMap> list = facade.getAppUserInfo(us);//����û����Ƿ�ע��
			if(list.size() > 0){
				us.setAge((String)list.get(0).getAt("age"));
				us.setUserName((String)list.get(0).getAt("user_name"));
				us.setId((Integer)list.get(0).getAt("id"));
				us.setUserHeight(String.valueOf(list.get(0).getAt("height")));
				us.setHead((String)list.get(0).getAt("head"));
			    isession.setAttribute("user", us);
//			SendMsg.SendOfflineMsgAfterSessionHandle(id,MessageManager.playerSet.get(id));
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		}
	}
	
}
