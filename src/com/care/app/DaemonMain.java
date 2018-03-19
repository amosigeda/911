package com.care.app;

import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;

import com.care.common.config.ServiceBean;
import com.godoing.rose.log.LogFactory;

/**
 * �����߳� �ػ�����
 * 
 * @author Administrator
 * 
 */
public class DaemonMain extends Thread {

	Log logger = LogFactory.getLog(DaemonMain.class);
	/* �߳��Ƿ����� */
	private boolean flag = false;
	private int stops = 600;// ʮ����
	/*  */

	private SynSysLogAction synSysLogAction = new SynSysLogAction();

	SimpleDateFormat DF_NORMAL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public DaemonMain() {
		/* ����spring���� */
		ServiceBean.getInstance();
	}

	public void run() {
		while (flag) {
			daemon();
			/* ��ͣ */
			try {
				// ��ͣʮ����
				Thread.sleep(1000 * stops);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void daemon() {
		try {
			if (!synSysLogAction.isFlag()) {
				synSysLogAction.setFlag(true);
				if (!synSysLogAction.isAlive()) {
					synSysLogAction.start();
				}
			} else {
				if (!synSysLogAction.isAlive()) {
					synSysLogAction = null;// �����
					synSysLogAction = new SynSysLogAction();
					synSysLogAction.setFlag(true);
					synSysLogAction.start();
				}
			}

			/* ��ͣ */
			try {
				// ��ͣʮs
				Thread.sleep(1000 * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}