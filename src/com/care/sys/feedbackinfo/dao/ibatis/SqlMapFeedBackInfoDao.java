package com.care.sys.feedbackinfo.dao.ibatis;

import java.util.*;

import com.care.sys.feedbackinfo.dao.FeedBackInfoDao;
import com.care.sys.feedbackinfo.domain.FeedBackInfo;
import com.care.sys.userinfo.dao.*;
import com.care.sys.userinfo.domain.*;
import com.godoing.rose.lang.*;

import org.apache.commons.logging.Log;
import com.godoing.rose.log.LogFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class SqlMapFeedBackInfoDao extends SqlMapClientDaoSupport implements FeedBackInfoDao{

Log logger = LogFactory.getLog(SqlMapFeedBackInfoDao.class);
	public SqlMapFeedBackInfoDao (){
	}

	public String getFeedBackInfoPK()throws DataAccessException{
		logger.debug("getFeedBackInfoPK()");
		return (String)getSqlMapClientTemplate().queryForObject("getFeedBackInfo_PK", null);
	}

	public Integer getFeedBackInfoCount(FeedBackInfo vo)throws DataAccessException{
		logger.debug("getFeedBackInfoCount(FeedBackInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getFeedBackInfoCount", vo);
	}

	public Integer getFeedBackInfoCount(DataParamMap dmap)throws DataAccessException{
		logger.debug("getFeedBackInfoCount(DataParamMap dmap)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getFeedBackInfoCountMap", dmap);
	}

	public List<DataMap> getFeedBackInfo(FeedBackInfo vo)throws DataAccessException{
		logger.debug("getFeedBackInfo(FeedBackInfo vo)");
		return getSqlMapClientTemplate().queryForList("getFeedBackInfo", vo);
	}

	public List<DataMap> getFeedBackInfoListByVo(FeedBackInfo vo)throws DataAccessException{
		logger.debug("getFeedBackInfoListByVo(FeedBackInfo vo)");
		return getSqlMapClientTemplate().queryForList("getFeedBackInfoListByVo", vo);
	}

	public int updateFeedBackInfo(FeedBackInfo vo)throws DataAccessException{
		logger.debug("updateFeedBackInfo(FeedBackInfo vo)");
		return getSqlMapClientTemplate().update("updateFeedBackInfo", vo);
	}

	public int insertFeedBackInfo(FeedBackInfo vo)throws DataAccessException{
		logger.debug("insertFeedBackInfo(FeedBackInfo vo)");
		return getSqlMapClientTemplate().update("insertFeedBackInfo", vo);
	}

	public int deleteFeedBackInfo(FeedBackInfo vo)throws DataAccessException{
		logger.debug("deleteFeedBackInfo(FeedBackInfo vo)");
		return getSqlMapClientTemplate().update("deleteFeedBackInfo", vo);
	}

	public int getSettingInfoCountByVo(FeedBackInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSettingInfoCountByVo(FeedBackInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getSettingInfoCountByVo", vo);
	}
}
