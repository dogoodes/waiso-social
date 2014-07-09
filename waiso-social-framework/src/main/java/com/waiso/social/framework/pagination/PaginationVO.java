package com.waiso.social.framework.pagination;

public abstract class PaginationVO implements java.io.Serializable{

	private Number numberOfRows = 0;
	private int maxRows = 0;
	private int pagina = 0;
	private int totalPagina = 0;
	
	
	public Number getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(Number numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	
	public void setTotalPagina(int total){
		this.totalPagina = total;
	}
	
	public int getTotalPagina(){
		if (numberOfRows.intValue() > maxRows){
			this.totalPagina = (int) Math.ceil((numberOfRows.intValue() / maxRows));
			int resto = numberOfRows.intValue() % maxRows;
			if ( resto > 0 ){
				this.totalPagina++;
			}
		}else{
			this.totalPagina = 1;
		}
		return totalPagina;
	}
}
