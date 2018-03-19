package com.care.sys.channelinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.channelinfo.domain.ChannelInfo;
import com.godoing.rose.lang.DataMap;

public interface ChannelInfoDao {
	
	public List<DataMap> getChannelInfo(ChannelInfo vo) throws DataAccessException;
	
	public List<DataMap> getChannelInfoListByVo(ChannelInfo vo) throws DataAccessException;
	
	public int getChannelInfoListCountByVo(ChannelInfo vo) throws DataAccessException;
	
	public int getChannelInfoMaxId(ChannelInfo vo) throws DataAccessException;
	
	public int insertChannelInfo(ChannelInfo vo) throws DataAccessException;

}
