package com.care.sys.dynamicInfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.appuserinfo.dao.ibatis.SqlMapAppUserInfoDao;
import com.care.sys.dynamicInfo.dao.DynamicInfoDao;
import com.care.sys.dynamicInfo.domain.DynamicInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapDynamicInfoDao extends SqlMapClientDaoSupport implements DynamicInfoDao {
	Log logger = LogFactory.getLog(SqlMapDynamicInfoDao.class);

	public List<DataMap> getDynamicInfoListByVo(DynamicInfo vo)
			throws DataAccessException {
		logger.debug("getDynamicInfoListByVo(DynamicInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDynamicInfoListByVo", vo);
	}

	public int getDynamicInfoListCountByVo(DynamicInfo vo)
			throws DataAccessException {
		logger.debug("getDynamicInfoListCountByVo(DynamicInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getDynamicInfoListCountByVo", vo);
	}

	public Integer insertDynamicInfo(DynamicInfo vo) throws DataAccessException {
		logger.debug("insertDynamicInfo(DynamicInfo vo)");
		return getSqlMapClientTemplate().update("insertDynamicInfo", vo);
	}

	public List<DataMap> getDynamicInfo(DynamicInfo vo)
			throws DataAccessException {
		logger.debug("getDynamicInfo(DynamicInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDynamicInfo", vo);
	}

	public int updateDynamicInfo(DynamicInfo vo) throws DataAccessException {
		logger.debug("updateDynamicInfo(DynamicInfo vo)");
		return  getSqlMapClientTemplate().update("updateDynamicInfo", vo);
	}

	public int deleteDynamicInfo(DynamicInfo vo) throws DataAccessException {
		logger.debug("deleteDynamicInfo(DynamicInfo vo)");
		return getSqlMapClientTemplate().update("deleteDynamicInfo", vo);
	}

	public List<DataMap> getDynamicFuncInfo(DynamicInfo vo)
			throws DataAccessException {
		logger.debug("getDynamicFuncInfo(DynamicInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDynamicFuncInfo", vo);
	}

}
