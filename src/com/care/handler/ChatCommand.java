package com.care.handler;


public class ChatCommand {
	
	public static final int MSG_TEST=0;

	public static final int MSG_SENDMSG=1;
	
	public static final int MSG_SENDMEDIA = 2;
	
	public static final int MSG_RECEIVEBASEDATA_RE = 3;
	
	public static final int MSG_RECEIVEMEDIA_RE = 4;
	
	public static final int MSG_SENDMEDIA_RE_RE = 5;
	
	public static final int USER_LOGIN = 6;
	
    private final int num;
 
    private ChatCommand(int num) {
        this.num = num;
    }
    public int toInt() {
        return num;
    }

    public static ChatCommand valueOf(String s) {
        s = s.toUpperCase();
        
        if ("MSG_SENDMSG".equals(s))
            return new ChatCommand(MSG_SENDMSG);
        if ("MSG_TEST".equals(s))
            return new ChatCommand(MSG_TEST);
        if ("MSG_SENDMEDIA".equals(s))
        	return new ChatCommand(MSG_SENDMEDIA);
        if ("MSG_RECEIVEBASEDATA_RE".equals(s))
        	return new ChatCommand(MSG_RECEIVEBASEDATA_RE);
        if ("MSG_RECEIVEMEDIA_RE".equals(s))
        	return new ChatCommand(MSG_RECEIVEMEDIA_RE);
        if ("MSG_SENDMEDIA_RE_RE".equals(s))
        	return new ChatCommand(MSG_SENDMEDIA_RE_RE);
        if ("USER_LOGIN".equals(s))
        	return new ChatCommand(USER_LOGIN);
        
        throw new IllegalArgumentException("Unrecognized command: " + s);
    }

}
