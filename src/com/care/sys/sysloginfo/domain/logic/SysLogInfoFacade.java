package com.care.sys.sysloginfo.domain.logic;

import java.util.List;

import com.care.sys.sysloginfo.domain.SysLogInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.lang.SystemException;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public interface SysLogInfoFacade {

	public String getSysLogInfoPK() throws SystemException;

	public Integer getSysLogInfoCount(SysLogInfo vo) throws SystemException;

	public Integer getSysLogInfoCount(DataParamMap dmap) throws SystemException;

	public List<DataMap> getSysLogInfo(SysLogInfo vo) throws SystemException;
	
	public List<DataMap> getSysLogInfoDesc(SysLogInfo vo) throws SystemException;

	public DataList getDataSysLogInfoListByVo(SysLogInfo vo)
			throws SystemException;

	public DataList getDataSysLogInfoListByMap(DataParamMap dmap)
			throws SystemException;

	public int updateSysLogInfo(SysLogInfo vo) throws SystemException;

	public int insertSysLogInfo(SysLogInfo vo) throws SystemException;

	public int insertSysLogInfo(String user, String log, String ip) throws SystemException;

	public int deleteSysLogInfo(SysLogInfo vo) throws SystemException;

	public int deleteSysLogInfos(SysLogInfo vo) throws SystemException;
	public void deleteSynSysLogInfo() throws SystemException;

	public void insertBeifenRecord(SysLogInfo vo)throws SystemException;

	public DataList getBeifenRecord(SysLogInfo sysLog)throws SystemException;

	public int updateOutDate(SysLogInfo vo) throws SystemException;

	public int selectSysLogInfoCount(SysLogInfo vo)throws SystemException;

	

}
