package com.care.handler;

public class TzCommand {
	private final int num;
	
	public static final int TSTART=0;
	
	public static final int TZ_LOGIN_REQ=1;
	
	public static final int TZ_CONNECT_REQ=2;
	
	public static final int TZ_WIFI_REQ=3;
	
	public static final int TZ_DIRDIALER_REQ=4;
	
	public static final int TZ_LBS_REQ=5;
	
	public static final int TZ_UL_VOICE_REQ=6;
	
	public static final int TZ_UL_VOICE=7;
	
	public static final int TZ_WIFI_REPLY=8;
	
	public static final int TZ_TIME_REQ=9;
	
	public static final int TZ_MULTIMEDIA_REQ = 10;
	
	public static final int TZ_OPERATEMEDIA_REQ = 11;
	
	public static final int TZ_BASEDATA_REQ = 12;
	
	public static final int UD = 13;
	
	public static final int LOGIN = 14;
	
	public static final int LK = 15;
	
	public static final int POWEROFF = 16;
	
	public static final int CR = 17;

	public static final int VERNO = 18;	
	
	public static final int SOS = 19;
	
	public static final int AL = 20;
	
	public static final int TS = 21;
	
	public static final int RG = 22;
		
	public static final int WAD = 23;
	
	
	private TzCommand(int num) {
	        this.num = num;
	    }
	 
	 public int toInt() {
	        return num;
	    }

	 public static TzCommand valueOf(String s) {
		 	s = s.split(",")[0];
	        s = s.toUpperCase();
	        if(s.startsWith("TS"))
	        	return new TzCommand(TSTART);
	        if ("TZ_LOGIN_REQ".equals(s))
	            return new TzCommand(TZ_LOGIN_REQ);
	        if ("TZ_CONNECT_REQ".equals(s))
	            return new TzCommand(TZ_CONNECT_REQ);
	        if ("TZ_WIFI_REQ".equals(s))
	            return new TzCommand(TZ_WIFI_REQ);
	        if ("TZ_DIRDIALER_REQ".equals(s))
	            return new TzCommand(TZ_DIRDIALER_REQ);
	        if ("TZ_LBS_REQ".equals(s))
	            return new TzCommand(TZ_LBS_REQ);
	        if ("TZ_UL_VOICE_REQ".equals(s))
	            return new TzCommand(TZ_UL_VOICE_REQ);
	        if ("TZ_UL_VOICE".equals(s))
	            return new TzCommand(TZ_UL_VOICE);
	        if ("TZ_WIFI_REPLY".equals(s))
	            return new TzCommand(TZ_WIFI_REPLY);
	        if ("TZ_TIME_REQ".equals(s))
	            return new TzCommand(TZ_TIME_REQ);
	        if("TZ_MULTIMEDIA_REQ".equals(s))
	        	return new TzCommand(TZ_MULTIMEDIA_REQ);
	        if("TZ_OPERATEMEDIA_REQ".equals(s))
	        	return new TzCommand(TZ_OPERATEMEDIA_REQ);
	        if("TZ_BASEDATA_REQ".equals(s))
	        	return new TzCommand(TZ_BASEDATA_REQ);
	        if(s.endsWith("UD"))
	        	return new TzCommand(UD);
	        if(s.endsWith("LOGIN"))
	        	return new TzCommand(LOGIN);	        
	        if(s.endsWith("LK"))
	        	return new TzCommand(LK);
	        if(s.endsWith("POWEROFF"))
	        	return new TzCommand(POWEROFF);
	        if(s.endsWith("CR")){
	        	return new TzCommand(CR);
	        }
	        if(s.endsWith("VERNO")){
	        	return new TzCommand(VERNO);
	        }
	        if(s.endsWith("TS")){
	        	return new TzCommand(TS);
	        }
	        
	        String[] messageArray = s.split("\\*");
	        String logo = messageArray[3].toString();
	        if(logo .substring(0,(logo.length()-1)).equals("SOS"))
	        	return new TzCommand(SOS);	        
	        if(s.endsWith("AL"))
	        	return new TzCommand(AL);	        
	        if(s.endsWith("RG"))
	        	return new TzCommand(RG);	        
	        if(s.endsWith("WAD"))
	        	return new TzCommand(WAD);	        
	        	        
	        throw new IllegalArgumentException("Unrecognized command: " + s);
	 }
}
