package com.care.app;

import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;

import com.godoing.rose.log.LogFactory;


/**
 * 
 * ϵͳ��־ɾ���߳�
 * @author Administrator
 *
 */
public class SynSysLogAction extends Thread {
	Log logger = LogFactory.getLog(SynSysLogAction.class);

	/* ���л�ȡ�Ƿ�ɹ� */
	boolean moIs = false;
	/* ��ͣʱ��1Сʱ */
	private int stops = 3600;
	/* �߳��Ƿ����� */
	private boolean flag = false;

	SimpleDateFormat DF_NORMAL = new SimpleDateFormat("yyyy-MM-dd");

	/* �̺߳��� */
	public void run() {
		while (flag) {
			// ͬ�����
			synData();
			try {
				// ��ͣ5m
				Thread.sleep(1000 * stops);
			} catch (InterruptedException e) {
			}
		}
	}

	/* ����ʱ����״̬��������ͬ�������б� */
	private void synData() {
		try {
			//logger.debug("===========ϵͳ��־ɾ���߳�����===============" + this);
			//ServiceBean.getInstance().getSysLogInfoFacade().deleteSynSysLogInfo();
		} catch (Exception e) {
			this.flag = false;
			logger.debug("SynSmsDayAction", e);
		}
	}

	public int getStops() {
		return stops;
	}

	public void setStops(int stops) {
		this.stops = stops;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
