package com.care.sys.companyinfo.domain.logic;

import java.util.List;

import com.care.sys.companyinfo.domain.CompanyInfo;
import com.care.sys.userinfo.form.UserInfoForm;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface CompanyInfoFacade {
	
	public List<DataMap> getCompanyInfo(CompanyInfo vo) throws SystemException;
	
	public DataList getCompanyInfoListByVo(CompanyInfo vo) throws SystemException;
	
	public int getCompanyInfoListCountByVo(CompanyInfo vo) throws SystemException;
	
	public int insertCompanyInfo(CompanyInfo vo) throws SystemException;
	
	public Integer getCompanyInfoMaxId(CompanyInfo vo) throws SystemException;
	
	public int getCompanyInfoCount(CompanyInfo vo) throws SystemException;
	
	public List<DataMap> getRelevanceInfo(CompanyInfo vo) throws SystemException;
	
	public int insertRelevanceInfo(CompanyInfo vo) throws SystemException;

	public int updateCompanyInfo(CompanyInfo vo) throws SystemException;

}
