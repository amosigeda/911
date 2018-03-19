package com.care.sys.settinginfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.settinginfo.dao.SettingInfoDao;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapSettingInfoDao extends SqlMapClientDaoSupport implements SettingInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapSettingInfoDao.class);
	public SqlMapSettingInfoDao(){
		
	}
	public List<DataMap> getSettingInfo(SettingInfo vo)
			throws DataAccessException {
		logger.debug("getSettingInfo(SettingInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSettingInfo", vo);
	}
	public int insertSettingInfo(SettingInfo vo) throws DataAccessException {
		logger.debug("insertSettingInfo(SettingInfo vo)");
		return getSqlMapClientTemplate().update("insertSettingInfo", vo);
	}
	public int updateSettingInfo(SettingInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateSettingInfo(SettingInfo vo)");
		return getSqlMapClientTemplate().update("updateSettingInfo", vo);
	}
	public int deleteSettingInfo(SettingInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("deleteSettingInfo(SettingInfo vo)");
		return getSqlMapClientTemplate().update("deleteSettingInfo", vo);
	}
	public int getSettingInfoCount(SettingInfo vo) throws DataAccessException {
		logger.debug("getSettingInfoCount(SettingInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getSettingInfoCount", vo);
	}
	public List<DataMap> getSettingInfoListByVo(SettingInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSettingInfoCount(SettingInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSettingInfoListByVo", vo);
	}
	public int getSettingInfoCountByVo(SettingInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSettingInfoCountByVo(SettingInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getSettingInfoCountByVo", vo);
	}
}
