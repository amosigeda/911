package com.care.sys.userinfo.domain.logic;

import java.util.List;

import com.care.sys.userinfo.dao.UserInfoDao;
import com.care.sys.userinfo.domain.UserInfo;
import com.care.sys.userinfo.form.UserInfoForm;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.lang.SystemException;

/* rose1.2 to files
 * rose anthor:wlb  1.0 version by time 2005/12/12
 * rose anthor:wlb  1.1 version by time 2006/06/06
 * rose anthor:wlb  1.2 version by time 2006/12/27*/

//ʵ�ֽӿ�ģʽ,ͨ��applicationContext-local.xml��ӳ�䣬�õ�bean����
public class UserInfoFacadeImpl
    implements UserInfoFacade {

    private UserInfoDao userInfoDao;
    public UserInfoFacadeImpl() {
    }

    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;     
    }

    public String getUserInfoPK() throws SystemException {
        return userInfoDao.getUserInfoPK();
    }

    public Integer getUserInfoCount(UserInfo vo) throws SystemException {
        return userInfoDao.getUserInfoCount(vo);
    }

    public Integer getUserInfoCount(DataParamMap dmap) throws
        SystemException {
        return userInfoDao.getUserInfoCount(dmap);
    }

    public List<DataMap> getUserInfo(UserInfo vo) throws SystemException {
        return userInfoDao.getUserInfo(vo);
    }

    public DataList getDataUserInfoListByVo(UserInfo vo) throws
        SystemException {
        DataList list = new DataList(userInfoDao.getUserInfoListByVo(vo));
        list.setTotalSize(userInfoDao.getUserInfoCount(vo));
        return list;
    }

    public DataList getDataUserInfoListByMap(DataParamMap dmap) throws
        SystemException {
        DataList list = new DataList(userInfoDao.getUserInfoListByMap(
            dmap));
        list.setTotalSize(userInfoDao.getUserInfoCount(dmap));
        return list;
    }

    public int updateUserInfo(UserInfo vo) throws SystemException {
        return userInfoDao.updateUserInfo(vo);
    }

    public int insertUserInfo(UserInfo vo) throws SystemException {
        return userInfoDao.insertUserInfo(vo);
    }

    public int deleteUserInfo(UserInfo vo) throws SystemException {
        return userInfoDao.deleteUserInfo(vo);
    }

    public boolean checkUser(UserInfo vo) throws SystemException {
        return userInfoDao.getUserInfoCount(vo).intValue() == 1;
    }
    public List<DataMap> getUsersInfoClient(UserInfoForm uf)
			throws SystemException {
		// TODO Auto-generated method stub
		return userInfoDao.getUsersInfoClient(uf);
	}

}
