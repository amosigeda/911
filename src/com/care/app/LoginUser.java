package com.care.app;
import com.godoing.rose.lang.DataMap;
public class LoginUser {
	private int id;
    //�û�����
    private String userCode;
    //�û���
    private String userName;
    //�û������飬��ɫ
    private String groupCode;
    //�û��Ľ�ɫ����
    private String code;
    /**
     * ����¼�û��ǿͻ��û����������������ʼ���� 
     */
    //�ͻ�����
    private String cvsCode;
    //�ͻ�ͨ�����п�ͷ4λ����Y001001����Y001
    private String spSubcodeStart;
    private String companyId;
    private String projectId;
    
    public LoginUser(){  	
    }
    
    public LoginUser(DataMap map) {
    	this.id=(Integer) (map.get("id"));
    	this.userCode=(String) (map.get("userCode"));
		this.userName=(String) (map.get("userName"));
		this.groupCode=(String) (map.get("groupCode"));
		this.code = (String) (map.get("code"));
		this.companyId = (String)(map.get("company_id"));
		this.projectId = (String)(map.get("project_id"));
		if("Cvs_Mt".equals(groupCode)||"HHZ".equals(groupCode)){
			findCvsInfo(userCode);
		}
    }
    
    private void findCvsInfo(String userCode){
//    	NewCvsInfoFacade info = ServiceBean.getInstance().getNewCvsInfoFacade();
//    	NewCvsInfo vo = new NewCvsInfo();
    //	vo.setCvsUserCode(userCode);
    	try{
//    		List<DataMap>list=info.getNewCvsInfo(vo);
//    		cvsCode=(String)list.get(0).get("code");
//    		Integer cvsId=(Integer)list.get(0).get("id");
//    		if(cvsId!=null){
//    			findSpsubcode(cvsId);
//    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    private void findSpsubcode(Integer cvsId){
//		SpSubCodeInfoFacade info = ServiceBean.getInstance().getSpSubCodeInfoFacade();
//		SpSubCodeInfo vo = new SpSubCodeInfo();
//		vo.setCvsId(cvsId.toString());
		try{
			//List<DataMap>list=info.getSpSubCodeInfo(vo);
			//Y006001��Y006002�����������ͻ���������ʱ���һ���ͻ�ȥ�ˣ�������Ӷ�Y006001��Y006002�����⴦��
			//String spcode=list.get(0).get("spSubCode").toString();
//			if("Y006001".equals(spcode)||"Y006002".equals(spcode)){
//				spSubcodeStart=spcode;
//			}else{
//				spSubcodeStart=spcode.substring(0, 4);
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getGroupCode() {
        return groupCode;
    }
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

	public String getCvsCode() {
		return cvsCode;
	}

	public void setCvsCode(String cvsCode) {
		this.cvsCode = cvsCode;
	}

	public String getSpSubcodeStart() {
		return spSubcodeStart;
	}

	public void setSpSubcodeStart(String spSubcodeStart) {
		this.spSubcodeStart = spSubcodeStart;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


}
