package com.care.sys.checkinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.checkinfo.dao.CheckInfoDao;
import com.care.sys.checkinfo.domain.CheckInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapCheckInfoDao extends SqlMapClientDaoSupport implements CheckInfoDao{
	Log logger = LogFactory.getLog(SqlMapCheckInfoDao.class);
	public SqlMapCheckInfoDao(){
		
	}

	public List<DataMap> getCheckInfo(CheckInfo vo)
			throws DataAccessException {
		logger.debug("getCheckInfo(CheckInfo vo)");
		return getSqlMapClientTemplate().queryForList("getCheckInfo", vo);
	}

	public int insertCheckInfo(CheckInfo vo) throws DataAccessException {
		logger.debug("insertCheckInfo(CheckInfo vo)");
		return getSqlMapClientTemplate().update("insertCheckInfo", vo);
	}

	public int updateCheckInfo(CheckInfo vo) throws DataAccessException {
		logger.debug("updateCheckInfo(CheckInfo vo)");
		return getSqlMapClientTemplate().update("updateCheckInfo", vo);
	}

	public List<DataMap> getCheckCodeInfo(CheckInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getCheckCodeInfo(CheckInfo vo)");
		return getSqlMapClientTemplate().queryForList("getCheckCodeInfo", vo);
	}

	public int getCheckInfoCount(CheckInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getCheckInfoCount(CheckInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getCheckInfoCount", vo);
	}

	public List<DataMap> getCheckInfoListByVo(CheckInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getCheckInfoListByVo(CheckInfo vo)");
		return getSqlMapClientTemplate().queryForList("getCheckInfoListByVo", vo);
	}

	public int insertCheckCodeInfo(CheckInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertCheckCodeInfo(CheckInfo vo)");
		return getSqlMapClientTemplate().update("insertCheckCodeInfo", vo);
	}

	public int getCheckInfoCountByVo(CheckInfo vo) throws DataAccessException {
		logger.debug("getCheckInfoCountByVo(CheckInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getCheckInfoCountByVo", vo);
	}

	public int deleteCheckInfo(CheckInfo del) throws DataAccessException {
		logger.debug("deleteCheckInfo(CheckInfo del)");
		return (Integer)getSqlMapClientTemplate().delete("deleteCheckInfo",del);
	}

}
