package com.care.sys.companyinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.companyinfo.dao.CompanyInfoDao;
import com.care.sys.companyinfo.domain.CompanyInfo;
import com.care.sys.userinfo.form.UserInfoForm;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;

public class SqlMapCompanyInfoDao extends SqlMapClientDaoSupport implements CompanyInfoDao {
	
	Log logger = LogFactory.getLog(SqlMapCompanyInfoDao.class);

	public List<DataMap> getCompanyInfo(CompanyInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getCompanyInfo(CompanyInfo vo)");
		return getSqlMapClientTemplate().queryForList("getCompanyInfo", vo);
	}

	public List<DataMap> getCompanyInfoListByVo(CompanyInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getCompanyInfoListByVo(CompanyInfo vo)");
		return getSqlMapClientTemplate().queryForList("getCompanyInfoListByVo", vo);
	}

	public int getCompanyInfoListCountByVo(CompanyInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getCompanyInfoListCountByVo(CompanyInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getCompanyInfoListCountByVo", vo);
	}

	public int insertCompanyInfo(CompanyInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertCompanyInfo(CompanyInfo vo)");
		return getSqlMapClientTemplate().update("insertCompanyInfo", vo);
	}

	public Integer getCompanyInfoMaxId(CompanyInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getCompanyInfoMaxId(CompanyInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getCompanyInfoMaxId", vo);
	}

	public List<DataMap> getRelevanceInfo(CompanyInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getRelevanceInfo(CompanyInfo vo)");
		return getSqlMapClientTemplate().queryForList("getRelevanceInfo", vo);
	}

	public int insertRelevanceInfo(CompanyInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertRelevanceInfo(CompanyInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("insertRelevanceInfo", vo);
	}

	public int getCompanyInfoCount(CompanyInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getCompanyInfoCount(CompanyInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getCompanyInfoCount", vo);
	}

	public int updateCompanyInfo(CompanyInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateCompanyInfo(CompanyInfo vo)");
		return getSqlMapClientTemplate().update("updateCompanyInfo", vo);
		
		
	}


}
