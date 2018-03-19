package com.care.common.http;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.Executors;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.care.app.MessageManager;
import com.care.app.ServerHandler;
import com.care.common.lang.LocationInfoTask;
import com.care.common.lang.Task;
import com.care.queue.SessionThread;

/**
 * ϵͳ�����ࡣ��Ҫ����ϵͳ�̼߳����
 * 
 * @author Administrator
 * 
 */
public class EndServlet extends HttpServlet {

	private static final long serialVersionUID = -5075963146474427895L;
	private static Logger logger = Logger.getLogger(EndServlet.class);
	public void init(ServletConfig servletConfig) {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a HH:mm:ss.SSS");
//		System.out.println("\n ϵͳ��ʼ����� " + df.format(new Date()));
//
//		// ////////////////////�������߳�////////////////////////
//		DaemonMain daemon = new DaemonMain();
//		daemon.setFlag(true);
//		daemon.start();

		MessageManager.init();
//		PropertyConfigurator.configure("log4j.properties");
		SessionThread st = new SessionThread();
		st.setDaemon(true);
		st.start();
		IoAcceptor acceptor = null;
		try {
			// 创建一个非阻塞的server端的Socket
			// acceptor = new NioSocketAcceptor(Runtime.getRuntime()
			// .availableProcessors() + 1);
			acceptor = new NioSocketAcceptor();
			// 设置过滤器（使用Mina提供的文本换行符编解码器）
			acceptor.getFilterChain().addLast(
					"codec",
					new ProtocolCodecFilter(
							new com.care.charset.CharsetCodecFactoryII(
									Charset.forName("utf-8"),"\r\n")));
			// LoggingFilter loggingFilter = new LoggingFilter();
			// loggingFilter.setSessionClosedLogLevel(LogLevel.NONE);
			// loggingFilter.setSessionCreatedLogLevel(LogLevel.DEBUG);
			// loggingFilter.setSessionOpenedLogLevel(LogLevel.INFO);
			// acceptor.getFilterChain().addLast("logger", loggingFilter);

			DefaultIoFilterChainBuilder filterChainBuilder = acceptor
					.getFilterChain();
			filterChainBuilder.addLast("threadPool", new ExecutorFilter(
					Executors.newCachedThreadPool()));

			// 设置读取数据的缓冲区大小
			acceptor.getSessionConfig().setReadBufferSize(102400);
			// 读写通道5分钟内无操作进入空闲状态
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 300);
			// 绑定逻辑处理器
			acceptor.setHandler(new ServerHandler());
			// 绑定端口
			acceptor.bind(new InetSocketAddress(8085));

			Scanner sc = new Scanner(System.in);
			logger.info("StartServer Success...     端口号为：" + 8085);
			logger.debug("StartServer Success...     端口号为：" + 8085);
			
			/*Timer timer = new Timer();
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 24);
			cal.set(Calendar.MINUTE, 5 );
			cal.set(Calendar.SECOND, 0);
			Date date = cal.getTime();
			Task task = new Task();
			timer.schedule(task, date, 24*60*60*1000);*/
//			while (sc.hasNext()) {
//				if (sc.next().equals("quit")) {
//					acceptor.unbind();
//					acceptor.dispose(true);
//					//st.stop();
//					break;
//				}
//			}
			
			//ZST@20151104
		/*	Timer timerLocation = new Timer(true);
			LocationInfoTask taskLocationInfo = new LocationInfoTask();
			timerLocation.schedule(taskLocationInfo, 0,10*60*1000);*/
			
		} catch (Exception e) {
			logger.error("服务端启动异常....", e);
			e.printStackTrace();
			acceptor.unbind();
			acceptor.dispose(true);
			if (!st.isAlive())
				st.stop();
		}

		///////////////////////�ͻ����߳�/////////////////
//		DaemonClientMain daemon = new DaemonClientMain();
//		daemon.setFlag(true);
//		daemon.start();
//		
		

		// ////////////////////�����߳�////////////////////////
		 /* ����èͬ�������� */
//		 PushMobileUserAction mobilepush = new PushMobileUserAction();
//		 mobilepush.setFlag(true);
//		 mobilepush.start();


	}

}
