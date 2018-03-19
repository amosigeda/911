package com.care.sys.monitorinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.monitorinfo.dao.MonitorInfoDao;
import com.care.sys.monitorinfo.domain.MonitorInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class SqlMapMonitorInfoDao extends SqlMapClientDaoSupport implements MonitorInfoDao{

Log logger = LogFactory.getLog(SqlMapMonitorInfoDao.class);
	public SqlMapMonitorInfoDao (){
	}

	public Integer getMonitorInfoCount(MonitorInfo vo)throws DataAccessException{
		logger.debug("getMonitorInfoCount(MonitorInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getMonitorInfoCount", vo);
	}

	public List<DataMap> getMonitorInfo(MonitorInfo vo)throws DataAccessException{
		logger.debug("getMonitorInfo(MonitorInfo vo)");
		return getSqlMapClientTemplate().queryForList("getMonitorInfo", vo);
	}

	public List<DataMap> getMonitorInfoListByVo(MonitorInfo vo)throws DataAccessException{
		logger.debug("getMonitorInfoListByVo(MonitorInfo vo)");
		return getSqlMapClientTemplate().queryForList("getMonitorInfoListByVo", vo);
	}
	
	public int updateMonitorInfo(MonitorInfo vo)throws DataAccessException{
		logger.debug("updateMonitorInfo(MonitorInfo vo)");
		return getSqlMapClientTemplate().update("updateMonitorInfo", vo);
	}

	public int insertMonitorInfo(MonitorInfo vo)throws DataAccessException{
		logger.debug("insertMonitorInfo(MonitorInfo vo)");
		return getSqlMapClientTemplate().update("insertMonitorInfo", vo);
	}

	public Integer getVisitInfoCount(MonitorInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getVisitInfoCount(MonitorInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getVisitInfoCount", vo);
	}

	public List<DataMap> getVisitInfo(MonitorInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getVisitInfo(MonitorInfo vo)");
		return getSqlMapClientTemplate().queryForList("getVisitInfo", vo);
	}

	public List<DataMap> getVisitInfoListByVo(MonitorInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getVisitInfoListByVo(MonitorInfo vo)");
		return getSqlMapClientTemplate().queryForList("getVisitInfoListByVo", vo);
	}

	public int updateVisitInfo(MonitorInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateVisitInfo(MonitorInfo vo)");
		return getSqlMapClientTemplate().update("updateVisitInfo", vo);
	}

	public int insertVisitInfo(MonitorInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertVisitInfo(MonitorInfo vo)");
		return getSqlMapClientTemplate().update("insertVisitInfo", vo);
	}

	public List<DataMap> getSwitchInfo(MonitorInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSwitchInfo(MonitorInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSwitchInfo", vo);
	}

	public int updateSwitchInfo(MonitorInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateSwitchInfo(MonitorInfo vo)");
		return getSqlMapClientTemplate().update("updateSwitchInfo", vo);
	}

	public int insertMonitorInfoBf(MonitorInfo io) throws DataAccessException {
		logger.debug("insertMonitorInfoBf(MonitorInfo io)");
		return getSqlMapClientTemplate().update("insertMonitorInfoBf", io);
	}

	public int insertVisitBackup(MonitorInfo vo) throws DataAccessException {
		logger.debug("insertVisitBackup(MonitorInfo vo");
		return getSqlMapClientTemplate().update("insertVisitBackup", vo);
	}

	public int deleteVisit(MonitorInfo vo) throws DataAccessException {
		logger.debug("deleteVisit(MonitorInfo vo)");
		return getSqlMapClientTemplate().update("deleteVisit", vo);
	}

	public int deleteMonitorInfo(MonitorInfo vo) throws DataAccessException {
		logger.debug("deleteMonitorInfo(MonitorInfo vo)");
		return getSqlMapClientTemplate().update("deleteMonitorInfo", vo);
	}
	

}
