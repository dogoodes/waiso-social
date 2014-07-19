package com.waiso.social.facebook;

import com.waiso.social.framework.log.GerenciadorLog;

import facebook4j.Account;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.auth.AccessToken;

public class AppFacebook {

	private static Facebook facebook = null;
	private final static Integer indexPageWaisoTI = 0;
	
	public static Facebook getFacebook(){
		if(facebook != null){
			Facebook facebook = new FacebookFactory().getInstance();
			facebook.setOAuthAppId("appId", "appSecret");
			facebook.setOAuthPermissions("commaSeparetedPermissions");
			facebook.setOAuthAccessToken(new AccessToken("accessToken", null));
		}
		return AppFacebook.facebook;
	}
	
	public static String getPageAccessToken(Integer indexPage) throws FacebookException {
		ResponseList<Account> accounts = facebook.getAccounts();
		Account yourPageAccount = accounts.get(indexPage);//if index 0 is your page account.
		return yourPageAccount.getAccessToken();
	}
	
	public static void log(User user){
		if(GerenciadorLog.isDebug(AppFacebook.class)){
			GerenciadorLog.debug(AppFacebook.class, "");
			GerenciadorLog.debug(AppFacebook.class, "");
		}
	}

	public static Integer getIndexpagewaisoti() {
		return indexPageWaisoTI;
	}
}