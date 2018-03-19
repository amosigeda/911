package com.care.sys.msginfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.msginfo.dao.MsgInfoDao;
import com.care.sys.msginfo.domain.MsgInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapMsgInfoDao extends SqlMapClientDaoSupport implements MsgInfoDao{

	Log logger = LogFactory.getLog(SqlMapMsgInfoDao.class);
	
	public List<DataMap> getMsgInfo(MsgInfo vo)
			throws DataAccessException {
		logger.debug("getMsgInfo(MsgInfo vo)");
		return getSqlMapClientTemplate().queryForList("getMsgInfo", vo);
	}
	public int insertMsgInfo(MsgInfo vo) throws DataAccessException {
		logger.debug("insertMsgInfo(MsgInfo vo)");
		return getSqlMapClientTemplate().update("insertMsgInfo", vo);
	}
	
	public int updateMsgInfo(MsgInfo vo) throws DataAccessException {
		logger.debug("updateMsgInfo(MsgInfo vo)");
		return getSqlMapClientTemplate().update("updateMsgInfo", vo);
	}
	public int getMsgInfoCount(MsgInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getMsgInfoCount(MsgInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getMsgInfoCount", vo);
	}
	public int deleteMsgInfo(MsgInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("deleteMsgInfo(MsgInfo vo)");
		return getSqlMapClientTemplate().update("deleteMsgInfo", vo);
	}
	public List<DataMap> getMsgInfoListByVo(MsgInfo vo)
			throws DataAccessException {
		logger.debug("getMsgInfoListByVo(MsgInfo vo)");
		return getSqlMapClientTemplate().queryForList("getMsgInfoListByVo", vo);
	}

}
