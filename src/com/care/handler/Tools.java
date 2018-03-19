package com.care.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

public class Tools {

	public static Long getTime(Long time) {
		Long t2 = new Date().getTime();
		Long diff = t2 - time;
		// Long day = diff / (1000 * 60 * 60 * 24); // 以天数为单位取整
		// Long hour = (diff / (60 * 60 * 1000) - day * 24); // 以小时为单位取整
		// Long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		Long secone = (diff / 1000);
		return secone;
	}

	public static Timestamp getTimestamp() {
		java.util.Date date = new java.util.Date();
		return new Timestamp(date.getTime());
	}
	
	public static Long getTime(){
		return new java.util.Date().getTime();
	}

	public static int[] RepdRemovalNum(int e[]) {
		Arrays.sort(e);
		int t[] = new int[e.length];
		int j = 0;
		t[0] = e[0];
		for (int i = 1; i < e.length; i++) {
			int q = e[i];
			if (t[j] != q)
				t[++j] = q;
		}
		return t;
	}

	public static String getBaseString(String url) {
		File file = new File(System.getProperty("user.dir") + "\\" + url);
		byte[] b = null;
		try {
			b = getContent(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(Base64.encodeBase64(b));
	}

	// properties
	private static Properties properties;

	public void getProperties(String file) {
		try {
			properties = new Properties();
			FileInputStream fis = new FileInputStream(new File(file));
			properties.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage() + " " + file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getStringFromProperty(String key) {
		String str = null;
		if (properties != null) {
			str = properties.getProperty(key);
		}
		return str;
	}

	public static PreparedStatement setPreStatementItems(
			PreparedStatement preStat, Object... inserts) {
		try {
			if (inserts == null) {
				return preStat;
			}
			int i = 1;
			for (Object v : inserts) {
				if (v.getClass().getSimpleName().equals("Integer")) {
					preStat.setInt(i, (Integer) v);
					i++;
				} else if (v.getClass().getSimpleName().equals("String")) {
					preStat.setString(i, (String) v);
					i++;
				} else if (v.getClass().getSimpleName().equals("Long")) {
					preStat.setLong(i, new Long(v.toString()));
					i++;
				} else
					continue;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preStat;
	}

	// file
	public static byte[] getContent(String filePath) throws IOException {
		File file = new File(filePath);

		long fileSize = file.length();

		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("file too big...");
			return null;
		}

		FileInputStream fi = new FileInputStream(file);

		byte[] buffer = new byte[(int) fileSize];

		int offset = 0;

		int numRead = 0;
		
		  while (offset < buffer.length && (numRead = fi.read(buffer, offset,
		  buffer.length - offset)) >= 0) {
		  
		  offset += numRead; }
		 
	//	byte[] b = new byte[fi.available()];
	//	offset = fi.read(b);

		if (offset != buffer.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());

		}
		fi.close();
		return buffer;
	}

	public static byte[] getContent(File file) throws IOException {
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("file too big...");
			return null;
		}
		FileInputStream fi = new FileInputStream(file);

		byte[] buffer = new byte[(int) fileSize];

		int offset = 0;

		int numRead = 0;

		while (offset < buffer.length
				&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {

			offset += numRead;
		}

		if (offset != buffer.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		fi.close();
		return buffer;
	}

	// 将byte数组写入文件
	public static void createFile(String path, byte[] content)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		fos.write(content);
		fos.close();
	}

	
}