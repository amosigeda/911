package com.care.sys.monitorinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.monitorinfo.domain.MonitorInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;


/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public interface MonitorInfoDao{

	public Integer getMonitorInfoCount(MonitorInfo vo)throws DataAccessException;

	public List<DataMap> getMonitorInfo(MonitorInfo vo)throws DataAccessException;

	public List<DataMap> getMonitorInfoListByVo(MonitorInfo vo)throws DataAccessException;

	public int updateMonitorInfo(MonitorInfo vo)throws DataAccessException;

	public int insertMonitorInfo(MonitorInfo vo)throws DataAccessException;
	
	public Integer getVisitInfoCount(MonitorInfo vo)throws DataAccessException;
	
	public List<DataMap> getVisitInfo(MonitorInfo vo) throws DataAccessException;

	public List<DataMap> getVisitInfoListByVo(MonitorInfo vo) throws DataAccessException;

	public int updateVisitInfo(MonitorInfo vo) throws DataAccessException;

	public int insertVisitInfo(MonitorInfo vo) throws DataAccessException;
	
	public List<DataMap> getSwitchInfo(MonitorInfo vo)throws DataAccessException;
	
	public int updateSwitchInfo(MonitorInfo vo) throws DataAccessException;

	public int insertMonitorInfoBf(MonitorInfo io)throws DataAccessException;
	
	public int insertVisitBackup(MonitorInfo vo) throws DataAccessException;
		
	public int deleteVisit(MonitorInfo vo) throws DataAccessException;

	public int deleteMonitorInfo(MonitorInfo vo) throws DataAccessException;
}
