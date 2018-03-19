package com.care.sys.rolefuncinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.rolefuncinfo.dao.RoleFuncInfoDao;
import com.care.sys.rolefuncinfo.domain.RoleFuncInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.log.LogFactory;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class SqlMapRoleFuncInfoDao extends SqlMapClientDaoSupport implements RoleFuncInfoDao{

Log logger = LogFactory.getLog(SqlMapRoleFuncInfoDao.class);
	public SqlMapRoleFuncInfoDao (){
	}

	public String getRoleFuncInfoPK()throws DataAccessException{
		logger.debug("getRoleFuncInfoPK()");
		return (String)getSqlMapClientTemplate().queryForObject("getRoleFuncInfo_PK", null);
	}

	public Integer getRoleFuncInfoCount(RoleFuncInfo vo)throws DataAccessException{
		logger.debug("getRoleFuncInfoCount(RoleFuncInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getRoleFuncInfoCount", vo);
	}

	public Integer getRoleFuncInfoCount(DataParamMap dmap)throws DataAccessException{
		logger.debug("getRoleFuncInfoCount(DataParamMap dmap)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getRoleFuncInfoCountMap", dmap);
	}

	public List<DataMap> getRoleFuncInfo(RoleFuncInfo vo)throws DataAccessException{
		logger.debug("getRoleFuncInfo(RoleFuncInfo vo)");
		return getSqlMapClientTemplate().queryForList("getRoleFuncInfo", vo);
	}

	public List<DataMap> getRoleFuncInfoListByVo(RoleFuncInfo vo)throws DataAccessException{
		logger.debug("getRoleFuncInfoListByVo(RoleFuncInfo vo)");
		return getSqlMapClientTemplate().queryForList("getRoleFuncInfoListByVo", vo);
	}

	public List<DataMap> getRoleFuncInfoListByMap(DataParamMap dmap)throws DataAccessException{
		logger.debug("getRoleFuncInfoListByMap(DataParamMap dmap)");
		return getSqlMapClientTemplate().queryForList("getRoleFuncInfoListByMap", dmap);
	}

	public int updateRoleFuncInfo(RoleFuncInfo vo)throws DataAccessException{
		logger.debug("updateRoleFuncInfo(RoleFuncInfo vo)");
		return getSqlMapClientTemplate().update("updateRoleFuncInfo", vo);
	}

	public int insertRoleFuncInfo(RoleFuncInfo vo)throws DataAccessException{
		logger.debug("insertRoleFuncInfo(RoleFuncInfo vo)");
		return getSqlMapClientTemplate().update("insertRoleFuncInfo", vo);
	}

	public int deleteRoleFuncInfo(RoleFuncInfo vo)throws DataAccessException{
		logger.debug("deleteRoleFuncInfo(RoleFuncInfo vo)");
		return getSqlMapClientTemplate().update("deleteRoleFuncInfo", vo);
	}
}
