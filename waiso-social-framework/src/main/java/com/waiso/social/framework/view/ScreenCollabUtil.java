package com.waiso.social.framework.view;


public class ScreenCollabUtil {
	
	public static boolean isSendScreen(String sendScreen){
		return (sendScreen != null && sendScreen.equals("true"));
	}
	
	public static boolean isRecoverScreen(String invoke){
		return (invoke != null && invoke.equals("recoverScreen"));
	}
}
