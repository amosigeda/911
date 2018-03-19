package com.care.sys.saleinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.saleinfo.domain.SaleInfo;
import com.godoing.rose.lang.DataMap;

public interface SaleInfoDao {
	
	public List<DataMap> getSaleInfo(SaleInfo vo) throws DataAccessException;
	
	public int insertSaleInfo(SaleInfo vo) throws DataAccessException;
	
	public int updateSaleInfo(SaleInfo vo) throws DataAccessException;
	
	public int deleteSaleInfo(SaleInfo vo) throws DataAccessException;
	
	public List<DataMap> getSaleInfoListByVo(SaleInfo vo) throws DataAccessException;
	
	public int getSaleInfoCount(SaleInfo vo) throws DataAccessException;
	
	public List<DataMap> getSaleInfoListGroupByProvince(SaleInfo vo) throws DataAccessException;
	
	public int getSaleInfoCountGroupByProvince(SaleInfo vo) throws DataAccessException;
	
	public List<DataMap> getAddDayGroupByTime(SaleInfo vo) throws DataAccessException;
	
	public int getCountAddDayGroupByTime(SaleInfo vo) throws DataAccessException;

}
