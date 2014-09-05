package com.waiso.social.facebook;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;

import facebook4j.Account;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;

public class AppFacebook {

	private static Facebook facebook = null;
	private final static Integer indexPageWaisoTI = 0;
	
	public static Facebook getFacebook(){
		if(AppFacebook.facebook == null){
			ConfigurationBuilder cb = new ConfigurationBuilder();
			
			Boolean debug = Boolean.valueOf(GerenciadorConfiguracao.getConfiguracao("facebook.debug"));
			cb.setDebugEnabled(debug);
			
			String appId = GerenciadorConfiguracao.getConfiguracao("facebook.oauth.appId");
			cb.setOAuthAppId(appId);
			
			String appSecret = GerenciadorConfiguracao.getConfiguracao("facebook.oauth.appSecret");
			cb.setOAuthAppSecret(appSecret);
			
			String accessToken = GerenciadorConfiguracao.getConfiguracao("facebook.oauth.accessToken");
			cb.setOAuthAccessToken(accessToken);
			
			String permissions = GerenciadorConfiguracao.getConfiguracao("facebook.oauth.permissions");
			cb.setOAuthPermissions(permissions);

			Facebook facebook = (new FacebookFactory(cb.build())).getInstance();
			AppFacebook.facebook = facebook;
		}
		return AppFacebook.facebook;
	}
	
	public static String getPageAccessToken(Integer indexPage) throws FacebookException {
		ResponseList<Account> accounts = facebook.getAccounts();
		Account yourPageAccount = accounts.get(indexPage);//if index 0 is your page account.
		return yourPageAccount.getAccessToken();
	}

	public static Integer getIndexpagewaisoti() {
		return indexPageWaisoTI;
	}
}