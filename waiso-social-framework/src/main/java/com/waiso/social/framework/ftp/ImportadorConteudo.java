package com.waiso.social.framework.ftp;

import java.io.Serializable;
import java.util.List;

public class ImportadorConteudo implements Serializable {

	private static final long serialVersionUID = 1932599965672925522L;
	private final Long cdEmpresa;
	private final ImportadorDado importadorDado;
	private final String importadorClassId;
	private String resultProcessing = null;
	
	public ImportadorConteudo(Long cdEmpresa, ImportadorDado importContent, String importadorClassId){
		this.cdEmpresa = cdEmpresa;
		this.importadorDado = importContent;
		this.importadorClassId = importadorClassId;
	}
	
	public Long getCdEmpresa() {
		return cdEmpresa;
	}
	public ImportadorDado getImportadorDado() {
		return importadorDado;
	}

	public String getImportadorClassId() {
		return importadorClassId;
	}
	
	public String getResultProcessing() {
		return resultProcessing;
	}

	public void setResultProcessing(String resultProcessing) {
		this.resultProcessing = resultProcessing;
	}
	
	public String toString(){
		String info =  "ImportadorConteudo CD_EMPRESA [" + cdEmpresa + "] ImportadorClassId [" + importadorClassId + "] ";
		if (importadorDado != null){
			info += importadorDado.toString();
		}
		return info;
	}
	
	
}
