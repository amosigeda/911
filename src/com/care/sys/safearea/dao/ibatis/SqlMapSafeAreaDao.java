package com.care.sys.safearea.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.safearea.dao.SafeAreaDao;
import com.care.sys.safearea.domain.SafeArea;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapSafeAreaDao extends SqlMapClientDaoSupport implements SafeAreaDao{
	
	Log logger = LogFactory.getLog(SqlMapSafeAreaDao.class);
	public SqlMapSafeAreaDao(){
		
	}
	public List<DataMap> getSafeArea(SafeArea vo)
			throws DataAccessException {
		logger.debug("getSafeArea(SafeArea vo)");
		return getSqlMapClientTemplate().queryForList("getSafeArea", vo);
	}
	public int insertSafeArea(SafeArea vo) throws DataAccessException {
		logger.debug("insertSafeArea(SafeArea vo)");
		return getSqlMapClientTemplate().update("insertSafeArea", vo);
	}
	
	public int updateSafeArea(SafeArea vo) throws DataAccessException {
		logger.debug("updateSafeArea(SafeArea vo)");
		return getSqlMapClientTemplate().update("updateSafeArea", vo);
	}
	public int getSafeAreaMaxCount(SafeArea vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSafeAreaMaxCount(SafeArea vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getSafeAreaMaxCount", vo);
	}
	public int deleteSafeArea(SafeArea vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("deleteSafeArea(SafeArea vo)");
		return getSqlMapClientTemplate().update("deleteSafeArea", vo);
	}
	public int getSafeAreaCount(SafeArea vo) throws DataAccessException {
		logger.debug("getSafeAreaCount(SafeArea vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getSafeAreaCount", vo);
	}
	public List<DataMap> getSafeAreaListByVo(SafeArea vo)
			throws DataAccessException {
		logger.debug("getSafeAreaListByVo(SafeArea vo)");
		return getSqlMapClientTemplate().queryForList("getSafeAreaListByVo", vo);
	}
}
