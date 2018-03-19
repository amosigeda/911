package com.care.sys.userinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.userinfo.dao.UserInfoDao;
import com.care.sys.userinfo.domain.UserInfo;
import com.care.sys.userinfo.form.UserInfoForm;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.log.LogFactory;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class SqlMapUserInfoDao extends SqlMapClientDaoSupport implements UserInfoDao{

Log logger = LogFactory.getLog(SqlMapUserInfoDao.class);
	public SqlMapUserInfoDao (){
	}

	public String getUserInfoPK()throws DataAccessException{
		logger.debug("getUserInfoPK()");
		return (String)getSqlMapClientTemplate().queryForObject("getUserInfo_PK", null);
	}

	public Integer getUserInfoCount(UserInfo vo)throws DataAccessException{
		logger.debug("getUserInfoCount(UserInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getUserInfoCount", vo);
	}

	public Integer getUserInfoCount(DataParamMap dmap)throws DataAccessException{
		logger.debug("getUserInfoCount(DataParamMap dmap)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getUserInfoCountMap", dmap);
	}

	public List<DataMap> getUserInfo(UserInfo vo)throws DataAccessException{
		logger.debug("getUserInfo(UserInfo vo)");
		return getSqlMapClientTemplate().queryForList("getUserInfo", vo);
	}

	public List<DataMap> getUserInfoListByVo(UserInfo vo)throws DataAccessException{
		logger.debug("getUserInfoListByVo(UserInfo vo)");
		return getSqlMapClientTemplate().queryForList("getUserInfoListByVo", vo);
	}

	public List<DataMap> getUserInfoListByMap(DataParamMap dmap)throws DataAccessException{
		logger.debug("getUserInfoListByMap(DataParamMap dmap)");
		return getSqlMapClientTemplate().queryForList("getUserInfoListByMap", dmap);
	}

	public int updateUserInfo(UserInfo vo)throws DataAccessException{
		logger.debug("updateUserInfo(UserInfo vo)");
		return getSqlMapClientTemplate().update("updateUserInfo", vo);
	}

	public int insertUserInfo(UserInfo vo)throws DataAccessException{
		logger.debug("insertUserInfo(UserInfo vo)");
		return getSqlMapClientTemplate().update("insertUserInfo", vo);
	}

	public int deleteUserInfo(UserInfo vo)throws DataAccessException{
		logger.debug("deleteUserInfo(UserInfo vo)");
		return getSqlMapClientTemplate().update("deleteUserInfo", vo);
		
	}
	public List<DataMap> getUsersInfoClient(UserInfoForm uf)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getUsersInfoClient(UserInfoForm uf)");
		return getSqlMapClientTemplate().queryForList("getUsersInfoClient", uf);
	}
}
