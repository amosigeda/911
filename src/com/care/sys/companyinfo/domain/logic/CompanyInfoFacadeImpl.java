package com.care.sys.companyinfo.domain.logic;

import java.util.List;

import com.care.sys.companyinfo.dao.CompanyInfoDao;
import com.care.sys.companyinfo.domain.CompanyInfo;
import com.care.sys.userinfo.form.UserInfoForm;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class CompanyInfoFacadeImpl implements CompanyInfoFacade {
	
	private CompanyInfoDao companyInfoDao;
	
	public CompanyInfoFacadeImpl(){
		
	}

	public void setCompanyInfoDao(CompanyInfoDao companyInfoDao) {
		this.companyInfoDao = companyInfoDao;
	}

	public List<DataMap> getCompanyInfo(CompanyInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.getCompanyInfo(vo);
	}

	public DataList getCompanyInfoListByVo(CompanyInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(companyInfoDao.getCompanyInfoListByVo(vo));
		list.setTotalSize(companyInfoDao.getCompanyInfoListCountByVo(vo));
		return list;
	}

	public int getCompanyInfoListCountByVo(CompanyInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.getCompanyInfoListCountByVo(vo);
	}

	public int insertCompanyInfo(CompanyInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.insertCompanyInfo(vo);
	}

	public Integer getCompanyInfoMaxId(CompanyInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.getCompanyInfoMaxId(vo);
	}

	public List<DataMap> getRelevanceInfo(CompanyInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.getRelevanceInfo(vo);
	}

	public int insertRelevanceInfo(CompanyInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.insertRelevanceInfo(vo);
	}

	public int getCompanyInfoCount(CompanyInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.getCompanyInfoCount(vo);
	}

	public int updateCompanyInfo(CompanyInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.updateCompanyInfo(vo);
	}

	
}
