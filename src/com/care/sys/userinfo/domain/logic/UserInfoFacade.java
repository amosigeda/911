package com.care.sys.userinfo.domain.logic;

import java.util.List;

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
//�ӿ�ģʽ
public interface UserInfoFacade {
	public List<DataMap> getUsersInfoClient(UserInfoForm uf) throws SystemException;

	public String getUserInfoPK() throws SystemException;

	public Integer getUserInfoCount(UserInfo vo) throws SystemException;

	public Integer getUserInfoCount(DataParamMap dmap) throws SystemException;

	public List<DataMap> getUserInfo(UserInfo vo) throws SystemException;

	public DataList getDataUserInfoListByVo(UserInfo vo) throws SystemException;

	public DataList getDataUserInfoListByMap(DataParamMap dmap) throws SystemException;

	public int updateUserInfo(UserInfo vo) throws SystemException;

	public int insertUserInfo(UserInfo vo) throws SystemException;

	public int deleteUserInfo(UserInfo vo) throws SystemException;

    public boolean checkUser(UserInfo vo) throws SystemException;
}
