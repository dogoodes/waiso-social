package com.waiso.social.framework.zip;

import java.io.Serializable;

public class ZipContent implements Serializable{

	private String name;
	private byte[] content;
	
	private ZipContent(){}
	
	public ZipContent(String name, byte[] content){
		this.name = name;
		this.content = content;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
}
