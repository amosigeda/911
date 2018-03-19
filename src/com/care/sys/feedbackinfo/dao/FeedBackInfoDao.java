package com.care.sys.feedbackinfo.dao;

import java.util.*;

import com.care.sys.feedbackinfo.domain.FeedBackInfo;
import com.godoing.rose.lang.*;

import org.springframework.dao.DataAccessException;


/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public interface FeedBackInfoDao{

	public String getFeedBackInfoPK()throws DataAccessException;

	public Integer getFeedBackInfoCount(FeedBackInfo vo)throws DataAccessException;

	public Integer getFeedBackInfoCount(DataParamMap dmap)throws DataAccessException;

	public List<DataMap> getFeedBackInfo(FeedBackInfo vo)throws DataAccessException;

	public List<DataMap> getFeedBackInfoListByVo(FeedBackInfo vo)throws DataAccessException;

	public int updateFeedBackInfo(FeedBackInfo vo)throws DataAccessException;

	public int insertFeedBackInfo(FeedBackInfo vo)throws DataAccessException;

	public int deleteFeedBackInfo(FeedBackInfo vo)throws DataAccessException;
}
