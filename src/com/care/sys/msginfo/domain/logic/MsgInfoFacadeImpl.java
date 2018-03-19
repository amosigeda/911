package com.care.sys.msginfo.domain.logic;

import java.util.List;

import com.care.sys.msginfo.dao.MsgInfoDao;
import com.care.sys.msginfo.domain.MsgInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class MsgInfoFacadeImpl implements MsgInfoFacade{

    private MsgInfoDao msgInfoDao;
	
	public void setMsgInfoDao(MsgInfoDao msgInfoDao){
		this.msgInfoDao = msgInfoDao;
	}
	
	public List<DataMap> getMsgInfo(MsgInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return msgInfoDao.getMsgInfo(vo);
	}

	public int getMsgInfoCount(MsgInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return msgInfoDao.getMsgInfoCount(vo);
	}

	public int insertMsgInfo(MsgInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return msgInfoDao.insertMsgInfo(vo);
	}

	public int updateMsgInfo(MsgInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return msgInfoDao.updateMsgInfo(vo);
	}

	public int deleteMsgInfo(MsgInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return msgInfoDao.deleteMsgInfo(vo);
	}

	public DataList getMsgInfoListByVo(MsgInfo vo) throws SystemException {
		DataList list = new DataList(msgInfoDao.getMsgInfoListByVo(vo));
		list.setTotalSize(msgInfoDao.getMsgInfoCount(vo));
		return list;
	}

}
