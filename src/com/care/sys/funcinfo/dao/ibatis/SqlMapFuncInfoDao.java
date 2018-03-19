package com.care.sys.funcinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.funcinfo.dao.FuncInfoDao;
import com.care.sys.funcinfo.domain.FuncInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.log.LogFactory;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class SqlMapFuncInfoDao extends SqlMapClientDaoSupport implements FuncInfoDao{

Log logger = LogFactory.getLog(SqlMapFuncInfoDao.class);
	public SqlMapFuncInfoDao (){
	}

	public String getFuncInfoPK()throws DataAccessException{
		logger.debug("getFuncInfoPK()");
		return (String)getSqlMapClientTemplate().queryForObject("getFuncInfo_PK", null);
	}

	public Integer getFuncInfoCount(FuncInfo vo)throws DataAccessException{
		logger.debug("getFuncInfoCount(FuncInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getFuncInfoCount", vo);
	}

	public Integer getFuncInfoCount(DataParamMap dmap)throws DataAccessException{
		logger.debug("getFuncInfoCount(DataParamMap dmap)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getFuncInfoCountMap", dmap);
	}

	public List<DataMap> getFuncInfo(FuncInfo vo)throws DataAccessException{
		logger.debug("getFuncInfo(FuncInfo vo)");
		return getSqlMapClientTemplate().queryForList("getFuncInfo", vo);
	}

	public List<DataMap> getFuncInfoListByVo(FuncInfo vo)throws DataAccessException{
		logger.debug("getFuncInfoListByVo(FuncInfo vo)");
		return getSqlMapClientTemplate().queryForList("getFuncInfoListByVo", vo);
	}

	public List<DataMap> getFuncInfoListByMap(DataParamMap dmap)throws DataAccessException{
		logger.debug("getFuncInfoListByMap(DataParamMap dmap)");
		return getSqlMapClientTemplate().queryForList("getFuncInfoListByMap", dmap);
	}

	public int updateFuncInfo(FuncInfo vo)throws DataAccessException{
		logger.debug("updateFuncInfo(FuncInfo vo)");
		return getSqlMapClientTemplate().update("updateFuncInfo", vo);
	}

	public int insertFuncInfo(FuncInfo vo)throws DataAccessException{
		logger.debug("insertFuncInfo(FuncInfo vo)");
		return getSqlMapClientTemplate().update("insertFuncInfo", vo);
	}

	public int deleteFuncInfo(FuncInfo vo)throws DataAccessException{
		logger.debug("deleteFuncInfo(FuncInfo vo)");
		return getSqlMapClientTemplate().update("deleteFuncInfo", vo);
	}
}
