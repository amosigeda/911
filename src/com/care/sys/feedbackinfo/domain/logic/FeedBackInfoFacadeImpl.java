package com.care.sys.feedbackinfo.domain.logic;

import java.util.*;

import com.care.sys.feedbackinfo.dao.FeedBackInfoDao;
import com.care.sys.feedbackinfo.domain.FeedBackInfo;
import com.godoing.rose.lang.*;

/* rose1.2 to files
 * rose anthor:wlb  1.0 version by time 2005/12/12
 * rose anthor:wlb  1.1 version by time 2006/06/06
 * rose anthor:wlb  1.2 version by time 2006/12/27*/

//ʵ�ֽӿ�ģʽ,ͨ��applicationContext-local.xml��ӳ�䣬�õ�bean����
public class FeedBackInfoFacadeImpl implements FeedBackInfoFacade {

    private FeedBackInfoDao feedBackInfoDao;
    public FeedBackInfoFacadeImpl() {
    }

    public void setFeedBackInfoDao(FeedBackInfoDao feedBackInfoDao) {
        this.feedBackInfoDao = feedBackInfoDao;     
    }

    public String getFeedBackInfoPK() throws SystemException {
        return feedBackInfoDao.getFeedBackInfoPK();
    }

    public Integer getFeedBackInfoCount(FeedBackInfo vo) throws SystemException {
        return feedBackInfoDao.getFeedBackInfoCount(vo);
    }

    public Integer getFeedBackInfoCount(DataParamMap dmap) throws
        SystemException {
        return feedBackInfoDao.getFeedBackInfoCount(dmap);
    }

    public List<DataMap> getFeedBackInfo(FeedBackInfo vo) throws SystemException {
        return feedBackInfoDao.getFeedBackInfo(vo);
    }

    public DataList getDataFeedBackInfoListByVo(FeedBackInfo vo) throws
        SystemException {
        DataList list = new DataList(feedBackInfoDao.getFeedBackInfoListByVo(vo));
        list.setTotalSize(feedBackInfoDao.getFeedBackInfoCount(vo));
        return list;
    }

    public int updateFeedBackInfo(FeedBackInfo vo) throws SystemException {
        return feedBackInfoDao.updateFeedBackInfo(vo);
    }

    public int insertFeedBackInfo(FeedBackInfo vo) throws SystemException {
        return feedBackInfoDao.insertFeedBackInfo(vo);
    }

    public int deleteFeedBackInfo(FeedBackInfo vo) throws SystemException {
        return feedBackInfoDao.deleteFeedBackInfo(vo);
    }

    public boolean checkFeedBack(FeedBackInfo vo) throws SystemException {
        return feedBackInfoDao.getFeedBackInfoCount(vo).intValue() == 1;
    }
}
