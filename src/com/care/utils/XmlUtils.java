package com.care.utils;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.godoing.rose.lang.DataMap;
//import com.synchrodata.sys.monitorinfo.domain.MonitorInfo;

public class XmlUtils {



	public XmlUtils() {
	}

	  /** 
	    * string2Document 
	    * 将字符串转为Document 
	    * @return 
	    * @param s xml格式的字符串 
	    */ 
	public static Document stringToDocument(String xmlString) throws Exception{
		Document doc = null; 
	     try{ 
	          doc = DocumentHelper.parseText(xmlString); 
	        }catch(Exception ex) {             
	           
	        	ex.printStackTrace(); 
	        } 
	       return doc; 
	}
	  /** 
	   * doc2XmlFile 
	   * 将Document对象保存为一个xml文件到本地 
	   * @return true:保存成功   flase:失败 
	   * @param filename 保存的文件名 
	   * @param document 需要保存的document对象 
	   */ 
	 public static boolean docToXmlFile(Document document,String filename) throws Exception{ 
	    boolean flag = true; 
	    try{ 
	          /* 将document中的内容写入文件中 */ 
	          //默认为UTF-8格式，指定为"utf-8" 
	           OutputFormat format = OutputFormat.createPrettyPrint(); 
	           format.setEncoding("utf-8"); 
	           XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)),format); 
	           writer.write(document); 
	           writer.close();             
	       }catch(Exception ex){ 
	            flag = false; 
	            ex.printStackTrace(); 
	       } 
	      return flag;        
	  }

	

}
