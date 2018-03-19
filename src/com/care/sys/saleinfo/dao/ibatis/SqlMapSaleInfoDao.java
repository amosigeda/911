package com.care.sys.saleinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.saleinfo.dao.SaleInfoDao;
import com.care.sys.saleinfo.domain.SaleInfo;
import com.godoing.rose.lang.DataMap;

public class SqlMapSaleInfoDao extends SqlMapClientDaoSupport implements SaleInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapSaleInfoDao.class);

	public int deleteSaleInfo(SaleInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("deleteSaleInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().update("deleteSaleInfo", vo);
	}

	public List<DataMap> getSaleInfo(SaleInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSaleInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSaleInfo", vo);
	}

	public int getSaleInfoCount(SaleInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSaleInfoCount(SaleInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getSaleInfoCount", vo);
	}

	public List<DataMap> getSaleInfoListByVo(SaleInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSaleInfoListByVo(SaleInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSaleInfoListByVo", vo);
	}

	public int insertSaleInfo(SaleInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertSaleInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().update("insertSaleInfo", vo);
	}

	public int updateSaleInfo(SaleInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateSaleInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().update("updateSaleInfo", vo);
	}

	public int getSaleInfoCountGroupByProvince(SaleInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSaleInfoCountGroupByProvince(SaleInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getSaleInfoCountGroupByProvince", vo);
	}

	public List<DataMap> getSaleInfoListGroupByProvince(SaleInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSaleInfoListGroupByProvince(SaleInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSaleInfoListGroupByProvince", vo);
	}

	public List<DataMap> getAddDayGroupByTime(SaleInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getAddDayGroupByTime(SaleInfo vo)");
		return getSqlMapClientTemplate().queryForList("getAddDayGroupByTime", vo);
	}

	public int getCountAddDayGroupByTime(SaleInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getCountAddDayGroupByTime(SaleInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getCountAddDayGroupByTime", vo);
	}

}
