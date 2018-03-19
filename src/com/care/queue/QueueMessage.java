package com.care.queue;

import org.apache.mina.core.session.IoSession;

public class QueueMessage {
	private int num;
	private Long time;
	private Long sendTime;
	private String msg;
	private IoSession session;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	public QueueMessage() {
		super();
	}

	public QueueMessage(int num, Long time, String msg, IoSession session,Long sendTime) {
		super();
		this.num = num;
		this.time = time;
		this.msg = msg;
		this.session = session;
		this.sendTime=sendTime;
	}

	
}
