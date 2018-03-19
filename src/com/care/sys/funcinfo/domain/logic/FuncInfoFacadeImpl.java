package com.care.sys.funcinfo.domain.logic;

import java.util.List;

import com.care.sys.funcinfo.dao.FuncInfoDao;
import com.care.sys.funcinfo.domain.FuncInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.lang.SystemException;

/* rose1.2 to files
 * rose anthor:wlb  1.0 version by time 2005/12/12
 * rose anthor:wlb  1.1 version by time 2006/06/06
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class FuncInfoFacadeImpl implements FuncInfoFacade {

	private FuncInfoDao funcInfoDao;
	public FuncInfoFacadeImpl (){
	}

	public void setFuncInfoDao(FuncInfoDao funcInfoDao){
		this.funcInfoDao = funcInfoDao;
	}

	public String getFuncInfoPK() throws SystemException{
		return funcInfoDao.getFuncInfoPK() ;
	}

	public Integer getFuncInfoCount(FuncInfo vo) throws SystemException{
		return funcInfoDao.getFuncInfoCount(vo);
	}

	public Integer getFuncInfoCount(DataParamMap dmap) throws SystemException{
		return funcInfoDao.getFuncInfoCount(dmap);
	}

	public List<DataMap> getFuncInfo(FuncInfo vo) throws SystemException{
		return funcInfoDao.getFuncInfo(vo);
	}

	public DataList getDataFuncInfoListByVo(FuncInfo vo) throws SystemException{
		DataList list = new DataList(funcInfoDao.getFuncInfoListByVo(vo));
		list.setTotalSize(funcInfoDao.getFuncInfoCount(vo));
		return list;
	}

	public DataList getDataFuncInfoListByMap(DataParamMap dmap) throws SystemException{
		DataList list = new DataList(funcInfoDao.getFuncInfoListByMap(dmap));
		list.setTotalSize(funcInfoDao.getFuncInfoCount(dmap));
		return list;
	}

	public int updateFuncInfo(FuncInfo vo) throws SystemException{
		return funcInfoDao.updateFuncInfo(vo);
	}

	public int insertFuncInfo(FuncInfo vo) throws SystemException{
		return funcInfoDao.insertFuncInfo(vo);
	}

	public int deleteFuncInfo(FuncInfo vo) throws SystemException{
		return funcInfoDao.deleteFuncInfo(vo);
	}

    public String getAllFuncTree() throws SystemException{
        StringBuffer dt = new StringBuffer("d = new dTree('d');\r\t");
        dt.append("d.add(0,-1,'Ȩ�޹���');\r\t"); //������
        FuncInfo vo = new FuncInfo();
        List<DataMap> list = getFuncInfo(vo);
        if (list.size() > 0 ) {
            dt(list, "super", dt, 0);
        }
        dt.append("document.write(d);");
        return dt.toString();
    }

    /*�ݹ���ʾ*/
    /**
     *
     * @param list List �������
     * @param fid Integer �����id
     * @param sb StringBuffer
     * @param treeid int ��treeid
     */
    int dt(List<DataMap> list, String sCode, StringBuffer sb, int treeid) {
        int s = treeid; //���������treeid
        for (int i = 0; i < list.size(); i++) {
            DataMap map = list.get(i);
            if (sCode.equals ((String) map.get("superCode"))) {
                treeid++; //������½�� +1
                sb.append("d.add(" + treeid + "," + s + ",'" +
                          map.get("funcName") +
                    "','#','','mainFrame','');\r\t");
                treeid = dt(list, (String) map.get("funcCode"), sb, treeid);
            }
        }
        return treeid;
    }
}
