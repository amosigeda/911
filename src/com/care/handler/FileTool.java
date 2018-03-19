package com.care.handler;

import java.io.File;

import com.care.app.MessageManager;

public class FileTool {
	static String system_path=MessageManager.system_path;
	//static String system_path=MessageManager.loca_path;
	static String file_path = "";
	public FileTool() {
		super();
	}

	public static String getAppHeadPath(int id) {
		file_path = "/img/app/" + id + "/headImg";
		File file = new File(system_path + file_path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		return file_path;
	}

	public static String getGroupImgPath(int group_id) {
		file_path = "/img/app/group/" + group_id;
		File file = new File(system_path + file_path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		return file_path;
	}

	public static String getAppChatImgPath(int id) {
		file_path = "/img/app/" + id + "/chatImg";
		File file = new File(system_path + file_path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		return file_path;
	}

	public static String getDeviceHeadPath(int id) {
		file_path = "/img/device/" + id + "/headImg";
		File file = new File(system_path + file_path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		return file_path;
	}
	
	public static String getGroupHeadPath(int group_id) {
		file_path = "/img/gp/" + group_id + "/hg";
		File file = new File(system_path + file_path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		return file_path;
	}

	public static String getDeviceChatImgPath(int id) {
		file_path = "/img/device/" + id + "/chatImg";
		File file = new File(system_path + file_path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		return file_path;
	}

	public static String getGroupVoicePath(int group_id) {
		file_path = "/voice/app/groupvoice/" + group_id;
		File file = new File(system_path + file_path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		return file_path;
	}

	public static String getAppChatVoicePath(int id) {
		file_path = "/voice/app/" + id + "/chatVoice";
		File file = new File(system_path + file_path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		return file_path;
	}

	public static String getDeviceVoicePath(int id) {
		file_path = "/voice/device/" + id + "/chatVoice";
		File file = new File(system_path + file_path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		return file_path;
	}
	
	public static String getMediaNamePath(int id) {
		file_path = "/txt/device/" + id + "/nameList";
		File file = new File(system_path + file_path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		return file_path;
	}
	
	public static String getMediaOperatePath(int id) {
		file_path = "/txt/app/" + id + "/operate";
		File file = new File(system_path + file_path);
		if (!file.exists() && !file.isDirectory())
			file.mkdirs();
		return file_path;
	}
}
