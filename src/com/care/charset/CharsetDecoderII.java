/**
 * @Description: 
 *
 * @Title: CharsetDecoder.java
 * @Package com.joyce.mina.code
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-3-19 上午11:35:49
 * @version V2.0
 */
package com.care.charset;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @Description:  字符解码
 *
 * @ClassName: CharsetDecoder
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-9-19 上午11:35:49
 * @version V2.0
 */
public class CharsetDecoderII extends CumulativeProtocolDecoder  {
	
	private static Logger logger = Logger.getLogger(CharsetDecoderII.class);
	
	private Charset charset; // 编码格式

	private String delimiter; // 文本分隔符

	private IoBuffer delimBuf; // 文本分割符匹配的变量

	// 定义常量值，作为每个IoSession中保存解码任务的key值
	//		private static String CONTEXT = CharsetDecoderII.class.getName()
	//		+ ".context";
	
	 private final AttributeKey CONTEXT = new AttributeKey(this.getClass(), "context");  
	 
	// 构造函数，必须指定Charset和文本分隔符
	public CharsetDecoderII(Charset charset, String delimiter) {
		this.charset = charset;
		this.delimiter = delimiter;
	}
/*
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		Context ctx = getContext(session);
		if (delimiter == null || "".equals(delimiter)) { // 如果文本换行符未指定，使用默认值
			delimiter = "\r\n";
		}
		if (charset == null) {
			charset = Charset.forName("utf-8");
		}
		decodeNormal(ctx, in, out);
	}
	
		// 解码
	private void decodeNormal(Context ctx, IoBuffer in,
			ProtocolDecoderOutput out) throws CharacterCodingException {
		// 取出未完成任务中已经匹配的文本换行符的个数
		int matchCount = ctx.getMatchCount();
		
		// 设置匹配文本换行符的IoBuffer变量
		if (delimBuf == null) {
            IoBuffer tmp = IoBuffer.allocate(2).setAutoExpand(true);
            tmp.putString(delimiter, charset.newEncoder());
            tmp.flip();
            delimBuf = tmp;
        }
		
		int oldPos = in.position(); // 解码的IoBuffer中数据的原始信息
        int oldLimit = in.limit();
        while (in.hasRemaining()) { // 变量解码的IoBuffer
            byte b = in.get();
            if (delimBuf.get(matchCount) == b) { // 匹配第matchCount位换行符成功
                matchCount++;               
                if (matchCount == delimBuf.limit()) { // 当前匹配到字节个数与文本换行符字节个数相同，匹配结束
                    int pos = in.position();   // 获得当前匹配到的position（position前所有数据有效）
                    in.limit(pos);
                    in.position(oldPos);   // position回到原始位置
                    ctx.append(in);   // 追加到Context对象未完成数据后面
                    in.limit(oldLimit); // in中匹配结束后剩余数据
                    in.position(pos);
                    IoBuffer buf = ctx.getBuf();
                    buf.flip();
                    buf.limit(buf.limit() - matchCount);// 去掉匹配数据中的文本换行符
                    try {
                        out.write(buf.getString(ctx.getDecoder())); // 输出解码内容
                    } finally {
                        buf.clear(); // 释放缓存空间
                    }
                    oldPos = pos;
                    matchCount = 0;
                }
            } else {
            	// 如果matchCount==0，则继续匹配
            	// 如果matchCount>0，说明没有匹配到文本换行符的中的前一个匹配成功字节的下一个字节，
            	// 跳转到匹配失败字符处，并置matchCount=0，继续匹配
                in.position(in.position()-matchCount);
                matchCount = 0;  // 匹配成功后，matchCount置空
            }
        }
        
        // 把in中未解码内容放回buf中
        in.position(oldPos);
        ctx.append(in);

        ctx.setMatchCount(matchCount);
	}
	
	
	
	
*/
	// 从IoSession中获取Context对象
	private Context getContext(IoSession session) {
		Context ctx;
		ctx = (Context) session.getAttribute(CONTEXT);
		if (ctx == null) {
			ctx = new Context();
			session.setAttribute(CONTEXT, ctx);
		}
		return ctx;
	}



	public void dispose(IoSession session) throws Exception {

	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {
	}

	// 内部类，保存IoSession解码时未完成的任务
	private class Context {
		private java.nio.charset.CharsetDecoder decoder;
		private IoBuffer buf; // 保存真实解码内容
		private int matchCount = 0; // 匹配到的文本换行符个数

		private Context() {
			decoder = charset.newDecoder();
			buf = IoBuffer.allocate(80).setAutoExpand(true);
		}

		// 重置
		public void reset() {
			matchCount = 0;
			decoder.reset();
		}

		// 追加数据
		public void append(IoBuffer in) {
			getBuf().put(in);
		}

		// ======get/set方法=====================
		public CharsetDecoder getDecoder() {
			return decoder;
		}

		public IoBuffer getBuf() {
			return buf;
		}

		public int getMatchCount() {
			return matchCount;
		}

		public void setMatchCount(int matchCount) {
			this.matchCount = matchCount;
		}
	} // end class Context;

	@Override
	protected boolean doDecode(IoSession session,  IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		logger.info("in CharsetDecoderII dodecode session is ==========================="+session);
		//session.write("1");
//		//test		
//		IoBuffer tempin = in;
//		byte[] bt = new byte[tempin.limit()];
//		tempin.get(bt);
//		StringBuffer sBuffer = new StringBuffer();
//		for(int i = 0; i < bt.length; i++) {
//			sBuffer.append((char)bt[i]);
//		}
//		logger.info("in CharsetDecoderII dodecode sBuffer is ==========================="+sBuffer.toString());
//		//testend
		int inRemain = in.remaining();
		logger.info("in CharsetDecodeII dodecode inRemain is =================="+inRemain);
		if(inRemain<4)
			return false;
		// 取出未完成任务中已经匹配的文本换行符的个数
				Context ctx = getContext(session);
				int matchCount = ctx.getMatchCount();
				// 设置匹配文本换行符的IoBuffer变量
				if (delimBuf == null) {
		            IoBuffer tmp = IoBuffer.allocate(2).setAutoExpand(true);
		            tmp.putString(delimiter, charset.newEncoder());
		            tmp.flip();
		            delimBuf = tmp;
		        }
				//	byte[] s = ioBufferToByte(in);  
		        //	System.out.println(new String(s,"utf-8"));  
				 
				int oldPos = in.position(); // 解码的IoBuffer中数据的原始信息
		        int oldLimit = in.limit();
		        while (in.hasRemaining()) { // 变量解码的IoBuffer
		            byte b = in.get();
		            if (delimBuf.get(matchCount) == b){ // 匹配第matchCount位换行符成功
		                matchCount++;
		                if (matchCount == delimBuf.limit()) { // 当前匹配到字节个数与文本换行符字节个数相同，匹配结束
		                    int pos = in.position();   // 获得当前匹配到的position（position前所有数据有效）
		                    in.limit(pos);
		                    in.position(oldPos);   // position回到原始位置
		                    ctx.append(in);   // 追加到Context对象未完成数据后面
		                    in.limit(oldLimit); // in中匹配结束后剩余数据
		                    in.position(pos);
		                    IoBuffer buf = ctx.getBuf();
		                    buf.flip();
		                    buf.limit(buf.limit() - matchCount);// 去掉匹配数据中的文本换行符
		                    try {
		                    	String dString = buf.getString(ctx.getDecoder());
		                        out.write(dString); // 输出解码内容
		                        logger.info("in CharsetDecoderII dodecode string is ==========================="+dString);
		                    } finally {
		                        buf.clear(); // 释放缓存空间
		                    }
		                    oldPos = pos;
		                    matchCount = 0;
		                }
		            } else {
		            	// 如果matchCount==0，则继续匹配
		            	// 如果matchCount>0，说明没有匹配到文本换行符的中的前一个匹配成功字节的下一个字节，
		            	// 跳转到匹配失败字符处，并置matchCount=0，继续匹配
		                in.position(in.position()-matchCount);
		                matchCount = 0;  // 匹配成功后，matchCount置空
		            }
		        }
		        // 把in中未解码内容放回buf中
		        in.position(oldPos);
		        ctx.append(in);
		        ctx.setMatchCount(matchCount);
		       // out.flush(arg0, arg1)
		       // out.flush(null, session);
		        return true;
	}
	 
	 public static byte [] ioBufferToByte(Object message)   
	 {  
	       if (!(message instanceof IoBuffer))   
	       {   
	           return null;   
	       }   
	       IoBuffer ioBuffer = (IoBuffer)message;   
	       ioBuffer.flip();
	       byte[] readByte = new byte[ioBuffer.limit()];  
	       try
	       {
	         ioBuffer.get(readByte);
	       }
	       catch (Exception e) 
	       {
	       System.out.println(e.toString());
	       }
	       return readByte;   
	 }
}


