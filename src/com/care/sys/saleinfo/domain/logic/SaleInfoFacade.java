package com.care.sys.saleinfo.domain.logic;

import java.util.List;

import com.care.sys.saleinfo.domain.SaleInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface SaleInfoFacade {
	
	public List<DataMap> getSaleInfo(SaleInfo vo) throws SystemException;
	
	public int insertSaleInfo(SaleInfo vo) throws SystemException;
	
	public int updateSaleInfo(SaleInfo vo) throws SystemException;
	
	public int deleteSaleInfo(SaleInfo vo) throws SystemException;
	
	public DataList getSaleInfoListByVo(SaleInfo vo) throws SystemException;
	
	public int getSaleInfoCount(SaleInfo vo) throws SystemException;
	
	public DataList getSaleInfoListGroupByProvince(SaleInfo vo) throws SystemException;
	
	public int getSaleInfoCountGroupByProvince(SaleInfo vo) throws SystemException;
	
	public DataList getAddDayGroupByTime(SaleInfo vo) throws SystemException;
	
	public int getCountAddDayGroupByTime(SaleInfo vo) throws SystemException;

}
