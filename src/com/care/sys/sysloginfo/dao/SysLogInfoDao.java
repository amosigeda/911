package com.care.sys.sysloginfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.sysloginfo.domain.SysLogInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;


/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public interface SysLogInfoDao{

	public String getSysLogInfoPK()throws DataAccessException;

	public Integer getSysLogInfoCount(SysLogInfo vo)throws DataAccessException;

	public Integer getSysLogInfoCount(DataParamMap dmap)throws DataAccessException;

	public List<DataMap> getSysLogInfo(SysLogInfo vo)throws DataAccessException;
	
	public List<DataMap> getSysLogInfoDesc(SysLogInfo vo)throws DataAccessException;

	public List<DataMap> getSysLogInfoListByMap(DataParamMap dmap)throws DataAccessException;

	public List<DataMap> getSysLogInfoListByVo(SysLogInfo vo)throws DataAccessException;

	public int updateSysLogInfo(SysLogInfo vo)throws DataAccessException;

	public int insertSysLogInfo(SysLogInfo vo)throws DataAccessException;

	public int deleteSysLogInfo(SysLogInfo vo)throws DataAccessException;
	
	public int deleteSysLogInfos(SysLogInfo vo)throws DataAccessException;

	public int insertBeifenRecord(SysLogInfo vo)throws DataAccessException;

	public List<DataMap> getBeifenRecord(SysLogInfo vo)throws DataAccessException;

	public int getBeifenRecordCount(SysLogInfo vo)throws DataAccessException;

	public int updateOutDate(SysLogInfo vo)throws DataAccessException;

	public int selectSysLogInfoCount(SysLogInfo vo)throws DataAccessException;


}
