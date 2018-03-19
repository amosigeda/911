package com.care.sys.msginfo.domain.logic;

import java.util.List;

import com.care.sys.msginfo.domain.MsgInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface MsgInfoFacade {

    public List<DataMap> getMsgInfo(MsgInfo vo)throws SystemException;
	
	public int getMsgInfoCount(MsgInfo vo)throws SystemException;
	
	public int insertMsgInfo(MsgInfo vo)throws SystemException;
	
	public int updateMsgInfo(MsgInfo vo)throws SystemException;
	
	public int deleteMsgInfo(MsgInfo vo)throws SystemException;
	
	public DataList getMsgInfoListByVo(MsgInfo vo) throws SystemException;
	
}
