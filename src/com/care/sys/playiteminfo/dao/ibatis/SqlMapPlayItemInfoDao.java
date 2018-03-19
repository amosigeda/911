package com.care.sys.playiteminfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.playiteminfo.dao.PlayItemInfoDao;
import com.care.sys.playiteminfo.domain.PlayItemInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapPlayItemInfoDao extends SqlMapClientDaoSupport implements PlayItemInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapPlayItemInfoDao.class);
	public SqlMapPlayItemInfoDao(){
		
	}
	public List<DataMap> getPlayItemInfo(PlayItemInfo vo)
			throws DataAccessException {
		logger.debug("getPlayItemInfo(PlayItemInfo vo)");
		return getSqlMapClientTemplate().queryForList("getPlayItemInfo", vo);
	}

	public int insertPlayItemInfo(PlayItemInfo vo) throws DataAccessException {
		logger.debug("insertPlayItemInfo(PlayItemInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("insertPlayItemInfo", vo);
	}
	public int updatePlayItemInfoControlType(PlayItemInfo vo)
			throws DataAccessException {
		logger.debug("updatePlayItemInfoControlType(PlayItemInfo vo)");
		return getSqlMapClientTemplate().update("updatePlayItemInfoControlType", vo);
	}

}
