package com.care.queue;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.care.app.MessageManager;
import com.care.app.Tools;

public class SessionThread extends Thread {

	public static int timelen=5;
	public static Queue<QueueMessage> msgQueue = new LinkedBlockingQueue<QueueMessage>();

	public SessionThread() {
		super();

	}

	@Override
	public void run() {
		while (true) {
			//synchronized (msgQueue) {
				if (!msgQueue.isEmpty()) {
					QueueMessage sendMsg = msgQueue.peek();
					if (sendMsg.getNum() >= 3) {
						msgQueue.remove();
						if (MessageManager.req_ok
								.containsKey(sendMsg.getTime())
								&& MessageManager.req_ok.get(sendMsg.getTime())
										.equals(sendMsg.getSession())) {
							msgQueue.remove();
							MessageManager.req_ok.remove(sendMsg.getSendTime());
							continue;
						}
						if (!sendMsg.getSession().isClosing())
							sendMsg.getSession().close(true);
					} else if (sendMsg.getNum() == 2) {
						if (MessageManager.req_ok
								.containsKey(sendMsg.getTime())
								&& MessageManager.req_ok.get(sendMsg.getTime())
										.equals(sendMsg.getSession())) {
							msgQueue.remove();
							MessageManager.req_ok.remove(sendMsg.getSendTime());
							continue;
						}
						if (Tools.getTime(sendMsg.getTime()) > timelen) {
							sendMsg.setNum(sendMsg.getNum() + 1);
							sendMsg.setTime(new Date().getTime());
							msgQueue.remove();
							msgQueue.add(sendMsg);
						}
					} else if (sendMsg.getNum() == 0 || sendMsg.getNum() == 1) {
						if (MessageManager.req_ok
								.containsKey(sendMsg.getTime())
								&& MessageManager.req_ok.get(sendMsg.getTime())
										.equals(sendMsg.getSession())) {
							MessageManager.req_ok.remove(sendMsg.getSendTime());
							msgQueue.remove();
							continue;
						}
						if (Tools.getTime(sendMsg.getTime()) > timelen) {
							sendMsg.getSession().write(sendMsg.getMsg());
							sendMsg.setNum(sendMsg.getNum() + 1);
							sendMsg.setTime(new Date().getTime());
							msgQueue.remove();
							msgQueue.add(sendMsg);
						} else {
							msgQueue.add(msgQueue.poll());
						}
					}
				}else{
					try {
						Thread.sleep(3000);
					//	System.out.println("sleep");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			//}
		}
	}

}
