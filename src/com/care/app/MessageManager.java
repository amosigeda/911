package com.care.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.care.handler.TzHandler;
import com.care.sys.appuserinfo.domain.AppUserInfo;

public class MessageManager {

	private static Logger logger = Logger.getLogger(MessageManager.class);
	/** 消息类型配置文件 */
	private static Tools properties;

	/** 会话 */
	private IoSession session;

	/** 消息 */
	private String message;
	
//	private String requestMessage;

	public static String system_path = "/usr/local/resin-4.0.46/webapps/GXCareDevice";

//  public static String system_path = "D:\\resin-3.1.10\\webapps\\GXCareDevice";
//	public static String system_path = "E:\\resin\\resin-3.1.10\\resin-3.1.10\\webapps\\GXCareDevice";

	// public static String system_path = System.getProperty("user.dir");

//	public static String file_ip = "http://ghero.cn:9888/GXCareDevice";
//	public static String file_ip = "http://server.ghero.cn:8080/GXCareDevice";
	public static String file_ip = "http://www.gpscarecare.com:8080/GXCareDevice";

	/** session map */
	public static Map<Long, IoSession> sessionSet = Collections
			.synchronizedMap(new HashMap<Long, IoSession>());

	/** login success session map */
	public static Map<Integer, IoSession> playerSet = Collections
			.synchronizedMap(new HashMap<Integer, IoSession>());
	
	/*ZST@20151214*/
	public static Map<String,IoSession> snSet = Collections
			.synchronizedMap(new HashMap<String,IoSession>());

	/** loginTz success session map */
	public static Map<Integer, IoSession> tzSet = Collections
			.synchronizedMap(new HashMap<Integer, IoSession>());

	// login success user map
	public static Map<Integer, AppUserInfo> userSet = Collections
			.synchronizedMap(new HashMap<Integer, AppUserInfo>());

	// group map
	public static Map<Integer, StringBuffer> groupSet = Collections
			.synchronizedMap(new HashMap<Integer, StringBuffer>());

	// ConcurrentLinkedQueue cc=new ConcurrentLinkedQueue();

	// public static BlockingQueue<IoSession> waitingSessions = new
	// LinkedBlockingQueue<IoSession>();

	public static Map<Long, IoSession> req_ok = Collections
			.synchronizedMap(new HashMap<Long, IoSession>());

	public static Map<IoSession, Integer> verifyCode = Collections
			.synchronizedMap(new HashMap<IoSession, Integer>());

	public static Map<Integer, List<String>> TzMsgSet = Collections
			.synchronizedMap(new HashMap<Integer, List<String>>());
	
	public static Map<Integer, List<String>> TzMediaSet = Collections
			.synchronizedMap(new HashMap<Integer, List<String>>());

	public static Map<Integer, Integer> TzMsgSize = Collections
			.synchronizedMap(new HashMap<Integer, Integer>());
	
	public static Map<Integer, Integer> TzTimeLen = Collections
			.synchronizedMap(new HashMap<Integer, Integer>());
	
	public static Map<Integer, Integer> TzMediaSize = Collections
			.synchronizedMap(new HashMap<Integer, Integer>());

	public static Map<Integer, Integer> TzMsgInd = Collections
			.synchronizedMap(new HashMap<Integer, Integer>());
	
	public static Map<Integer, Integer> TzLockMap = Collections
			.synchronizedMap(new HashMap<Integer, Integer>());
	
	public static Map<Integer, Integer> TzMediaInd = Collections
			.synchronizedMap(new HashMap<Integer, Integer>());

	public MessageManager(IoSession session, Object message) {
		this.session = session;
		this.message = message.toString();
	}

	public static void init() {
		properties = new Tools();
		properties.getProperties(system_path+java.io.File.separator+"res" + java.io.File.separator
				+ "messageType.ini");
		return;
	}

	/**
	 * 接收消息，并将消息发送到相对应的消息处理者处理
	 */
	public void msgTransfer() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		if(message.trim().startsWith("[") && message.trim().endsWith("]")){
			TzHandler handler = new TzHandler(session,message);
			handler.handle();
			
		}else{
			String req = "";
			String type = "";
			JSONObject js = JSONObject.fromObject(message);
			req = js.getString("request") == null ? null : js.getString("request");
			if (req != null) {
				type = req.substring(0, req.indexOf("_"));				

				MessageManager.playerSet.containsKey(js.has("user_id")?js.get("user_id"):0);
			}
			logger.info("======= MessageManager: Type= "+type);
			String pro = properties.getStringFromProperty(type);
			logger.info("======= MessageManager: Type=" +pro);
			Class<?> transmission = Class.forName(pro);
			Constructor<?> test = transmission.getConstructor(IoSession.class,
					String.class);
			AbstractHandler handler = (AbstractHandler) test.newInstance(
					session, message);
			handler.handle();
		}		
	}
}
