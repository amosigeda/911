/**
 * @Description: 
 *
 * @Title: CharsetCodecFactory.java
 * @Package com.joyce.mina.code.factory
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-3-19 上午11:34:14
 * @version V2.0
 */
package com.care.charset;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;



/**
 * @Description: 字符编码、解码工厂类，编码过滤工厂
 *
 * @ClassName: CharsetCodecFactory
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-3-19 上午11:34:14
 * @version V2.0
 */
public class CharsetCodecFactoryII implements ProtocolCodecFactory {
	private Charset charset; // 编码格式

	private String delimiter; // 文本分隔符

	public CharsetCodecFactoryII(Charset charset, String delimiter) {
		this.charset = charset;
		this.delimiter = delimiter;
	}

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return new CharsetDecoderII(charset, delimiter);
	}

	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return new CharsetEncoderII(charset, delimiter);
	}

}
