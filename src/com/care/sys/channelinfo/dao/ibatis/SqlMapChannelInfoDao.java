package com.care.sys.channelinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.channelinfo.dao.ChannelInfoDao;
import com.care.sys.channelinfo.domain.ChannelInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapChannelInfoDao extends SqlMapClientDaoSupport implements ChannelInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapChannelInfoDao.class);

	public List<DataMap> getChannelInfo(ChannelInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getChannelInfo(ChannelInfo vo)");
		return getSqlMapClientTemplate().queryForList("getChannelInfo", vo);
	}

	public List<DataMap> getChannelInfoListByVo(ChannelInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getChannelInfoListByVo(ChannelInfo vo)");
		return getSqlMapClientTemplate().queryForList("getChannelInfoListByVo", vo);
	}

	public int getChannelInfoListCountByVo(ChannelInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getChannelInfoListCountByVo(ChannelInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getChannelInfoListCountByVo", vo);
	}

	public int getChannelInfoMaxId(ChannelInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getChannelInfoMaxId(ChannelInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getChannelInfoMaxId", vo);
	}

	public int insertChannelInfo(ChannelInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertChannelInfo(ChannelInfo vo)");
		return getSqlMapClientTemplate().update("insertChannelInfo", vo);
	}

}
