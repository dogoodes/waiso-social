package com.waiso.social.framework.utilitario;

import java.util.UUID;

public class UUIDUtils {

	public static String generateUUID(){
		return UUID.randomUUID().getMostSignificantBits() + "";
	}
}