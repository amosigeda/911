package com.care.sys.sysloginfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.sysloginfo.dao.SysLogInfoDao;
import com.care.sys.sysloginfo.domain.SysLogInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.log.LogFactory;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class SqlMapSysLogInfoDao extends SqlMapClientDaoSupport implements SysLogInfoDao{

Log logger = LogFactory.getLog(SqlMapSysLogInfoDao.class);
	public SqlMapSysLogInfoDao (){
	}

	public String getSysLogInfoPK()throws DataAccessException{
		logger.debug("getSysLogInfoPK()");
		return (String)getSqlMapClientTemplate().queryForObject("getSysLogInfo_PK", null);
	}

	public Integer getSysLogInfoCount(SysLogInfo vo)throws DataAccessException{
		logger.debug("getSysLogInfoCount(SysLogInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getSysLogInfoCount", vo);
	}

	public Integer getSysLogInfoCount(DataParamMap dmap)throws DataAccessException{
		logger.debug("getSysLogInfoCount(DataParamMap dmap)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getSysLogInfoCountMap", dmap);
	}

	public List<DataMap> getSysLogInfo(SysLogInfo vo)throws DataAccessException{
		logger.debug("getSysLogInfo(SysLogInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSysLogInfo", vo);
	}
	
	public List<DataMap> getSysLogInfoDesc(SysLogInfo vo)throws DataAccessException{
		logger.debug("getSysLogInfoDesc(SysLogInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSysLogInfoDesc", vo);
	}

	public List<DataMap> getSysLogInfoListByVo(SysLogInfo vo)throws DataAccessException{
		logger.debug("getSysLogInfoListByVo(SysLogInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSysLogInfoListByVo", vo);
	}

	public List<DataMap> getSysLogInfoListByMap(DataParamMap dmap)throws DataAccessException{
		logger.debug("getSysLogInfoListByMap(DataParamMap dmap)");
		return getSqlMapClientTemplate().queryForList("getSysLogInfoListByMap", dmap);
	}

	public int updateSysLogInfo(SysLogInfo vo)throws DataAccessException{
		logger.debug("updateSysLogInfo(SysLogInfo vo)");
		return getSqlMapClientTemplate().update("updateSysLogInfo", vo);
	}

	public int insertSysLogInfo(SysLogInfo vo)throws DataAccessException{
		logger.debug("insertSysLogInfo(SysLogInfo vo)");
		return getSqlMapClientTemplate().update("insertSysLogInfo", vo);
	}

	public int deleteSysLogInfo(SysLogInfo vo)throws DataAccessException{
		logger.debug("deleteSysLogInfo(SysLogInfo vo)");
		return getSqlMapClientTemplate().update("deleteSysLogInfo", vo);
	}
	
	public int deleteSysLogInfos(SysLogInfo vo)throws DataAccessException{
		logger.debug("deleteSysLogInfos(SysLogInfo vo)");
		return getSqlMapClientTemplate().update("deleteSysLogInfos", vo);
	}

	public int insertBeifenRecord(SysLogInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertBeifenRecord(SysLogInfo vo)");
		return getSqlMapClientTemplate().update("insertBeifenRecord", vo);
	}

	public List<DataMap> getBeifenRecord(SysLogInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getBeifenRecord(SysLogInfo vo)");
		return getSqlMapClientTemplate().queryForList("getBeifenRecord", vo);
	}

	public int getBeifenRecordCount(SysLogInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getBeifenRecordCount(SysLogInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getBeifenRecordCount", vo);
	}

	public int updateOutDate(SysLogInfo vo) throws DataAccessException {
		logger.debug("updateOutDate(SysLogInfo vo)");
		return getSqlMapClientTemplate().update("updateOutDate", vo);
		
	}

	public int selectSysLogInfoCount(SysLogInfo vo) throws DataAccessException {
		logger.debug("selectSysLogInfoCount(SysLogInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("selectSysLogInfoCount",vo);
	}
}
