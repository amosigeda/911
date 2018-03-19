package com.care.common.lang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class Constant {

	public final static String RESULTCODE = "resultCode";

	public final static String EXCEPTION = "exception";

	public final static int EXCEPTION_CODE = -1; // 锟斤拷台锟届常

	public final static int SUCCESS_CODE = 1; // 锟缴癸拷

	public final static int FAIL_CODE = 0; // 失锟斤拷

	public final static String LOCATION_URL = "http://apilocate.amap.com/position"; // 锟斤拷位锟斤拷锟杰碉拷址

	public final static String SERVER_IP = "121.196.232.11";

	public final static String KEY ="1c1c642bd81c287fd16239ddc0eb85c6"; 
			//"bcfbf9ebf25a4d0bf86fe9f416b62264";
	// 鐢ㄦ埛澶村儚鍦板潃
	public final static String USER_SAVE = "/upload/user/";

	// 璁惧澶村儚鍦板潃
	public final static String DEVICE_SAVE = "/upload/device/";

	// 鍦扮悆鍗婂緞
	public final static double EARTH_RADIUS = 6378.137;

	//鏈夋晥鏁版嵁
	public final static double EFFERT_DATA = 100.0;
	
	public final static String SPLITE = "_";
	
	public final static long DATE_JIANGE = 20 * 60 * 1000L;
	
	public final static long LOW = 30;
	/**
	 * 锟斤拷锟絠d,锟街革拷
	 * 
	 * @param userId
	 */
	public static String getUserIdToApp(String userId) {
		String tmp = userId.substring(userId.lastIndexOf("_") + 1);

		return tmp;
	}

	/**
	 * 转锟斤拷
	 */
	public static String transCodingToUtf(String transString) {
		String tmp = "";
		try {
			tmp = URLDecoder.decode(transString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmp;
	}

	/**
	 * 锟斤拷取锟斤拷前锟斤拷锟节碉拷前锟斤拷锟斤拷(前锟斤拷锟角革拷锟斤拷,-7锟斤拷示前锟斤拷锟斤拷)
	 * 
	 * @param daysAgo
	 * @return
	 */
	public static String getDaysAgoCondition(String filed, int daysAgo) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();

		c.add(Calendar.DATE, daysAgo);
		Date day = c.getTime();
		return filed + " between '" + dateFormat.format(day)
				+ " 00:00:00' and '" + dateFormat.format(now) + " 23:59:59'";
	}

	/**
	 * 锟侥硷拷锟斤拷锟斤拷
	 * 
	 * @param path
	 * @param content
	 * @throws IOException
	 */
	public static void createFileContent(String path, String fileName,
			byte[] content) throws IOException {
		createFile(path);
		FileOutputStream fos = new FileOutputStream(path + "/" + fileName);
		fos.write(content);
		fos.close();
	}

	/**
	 * 锟侥硷拷路锟斤拷锟斤拷锟斤拷锟斤拷,锟津创斤拷
	 * 
	 * @param path
	 */
	public static void createFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void deleteFile(String deletePath) {
		File file = new File(deletePath);
		if (file.exists() && file.isDirectory()) {
			String[] arr = file.list();
			for (String tmp : arr) {
				new File(deletePath + "/" + tmp).delete();
			}
		}
	}

	public static String getUniqueCode(String unique) {
		Calendar c = Calendar.getInstance();
		long now = c.getTime().getTime(); // 锟斤拷时锟斤拷锟絤s锟斤拷确锟斤拷唯一锟斤拷
		return unique + "_" + String.valueOf(now);
	}

	public static String getDownloadPath(String url, String port, String path) {
		String tmp = url + port + path;
		return tmp;
	}

	/**
	 * 缁忕含搴﹀�璁＄畻璺濈
	 */
	public static boolean getDistance(double lat1, double lng1, double lat2,
			double lng2,double range) {

		double radlat1 = getRadian(lat1);
		double radlat2 = getRadian(lat2);

		double lat_distance = radlat1 - radlat2; // 绾害宸�
		double lng_distance = getRadian(lng1) - getRadian(lng2); // 缁忓害宸�

		double distance = Math.asin(Math.sqrt(Math.pow(
				Math.sin(lat_distance / 2), 2)
				+ Math.cos(radlat1)
				* Math.cos(radlat2)
				* Math.pow(Math.sin(lng_distance / 2), 2)));

		distance = 2 * distance * EARTH_RADIUS * 1000;
		
//		distance = Math.round(distance);
		System.out.println("距离为"+distance);
		if(distance >= range){
			return true;
		}
		return false;
	}

	private static double getRadian(double degree) {
		return degree * Math.PI / 180;

	}
//	public static void main(String[] args) {
//		System.out.println("褰撳墠鐨勮窛绂�+getDistance(22.554262,113.941146,22.554277,113.941752));
//	}
}
