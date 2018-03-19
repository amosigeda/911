package com.care.sys.msginfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.msginfo.domain.MsgInfo;
import com.godoing.rose.lang.DataMap;

public interface MsgInfoDao {

    public int insertMsgInfo(MsgInfo vo) throws DataAccessException;
	
	public List<DataMap> getMsgInfo(MsgInfo vo) throws DataAccessException;
	
	public int updateMsgInfo(MsgInfo vo) throws DataAccessException;

	public int getMsgInfoCount(MsgInfo vo) throws DataAccessException;
	
	public int deleteMsgInfo(MsgInfo vo) throws DataAccessException;
	
	public List<DataMap> getMsgInfoListByVo(MsgInfo vo) throws DataAccessException;
}
