package com.care.sys.feedbackinfo.domain.logic;

import java.util.*;

import com.care.sys.feedbackinfo.domain.FeedBackInfo;
import com.godoing.rose.lang.*;
/* rose1.2 to files
 * rose anthor:wlb  1.0 version by time 2005/12/12
 * rose anthor:wlb  1.1 version by time 2006/06/06
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
//�ӿ�ģʽ
public interface FeedBackInfoFacade {

	public String getFeedBackInfoPK() throws SystemException;

	public Integer getFeedBackInfoCount(FeedBackInfo vo) throws SystemException;

	public Integer getFeedBackInfoCount(DataParamMap dmap) throws SystemException;

	public List<DataMap> getFeedBackInfo(FeedBackInfo vo) throws SystemException;

	public DataList getDataFeedBackInfoListByVo(FeedBackInfo vo) throws SystemException;

	public int updateFeedBackInfo(FeedBackInfo vo) throws SystemException;

	public int insertFeedBackInfo(FeedBackInfo vo) throws SystemException;

	public int deleteFeedBackInfo(FeedBackInfo vo) throws SystemException;

    public boolean checkFeedBack(FeedBackInfo vo) throws SystemException;
}
