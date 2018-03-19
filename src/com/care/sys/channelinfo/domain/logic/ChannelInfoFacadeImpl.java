package com.care.sys.channelinfo.domain.logic;

import java.util.List;

import com.care.sys.channelinfo.dao.ChannelInfoDao;
import com.care.sys.channelinfo.domain.ChannelInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class ChannelInfoFacadeImpl implements ChannelInfoFacade{
	
	private ChannelInfoDao channelInfoDao;
	
	public ChannelInfoFacadeImpl(){
		
	}

	public void setChannelInfoDao(ChannelInfoDao channelInfoDao) {
		this.channelInfoDao = channelInfoDao;
	}

	public List<DataMap> getChannelInfo(ChannelInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return channelInfoDao.getChannelInfo(vo);
	}

	public DataList getChannelInfoListByVo(ChannelInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(channelInfoDao.getChannelInfoListByVo(vo));
		list.setTotalSize(channelInfoDao.getChannelInfoListCountByVo(vo));
		return list;
	}

	public int getChannelInfoListCountByVo(ChannelInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return channelInfoDao.getChannelInfoListCountByVo(vo);
	}

	public int getChannelInfoMaxId(ChannelInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return channelInfoDao.getChannelInfoMaxId(vo);
	}

	public int insertChannelInfo(ChannelInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return channelInfoDao.insertChannelInfo(vo);
	}

}
