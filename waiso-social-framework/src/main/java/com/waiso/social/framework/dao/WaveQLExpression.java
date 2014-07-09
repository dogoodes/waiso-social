package com.waiso.social.framework.dao;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.waiso.social.framework.utilitario.StringUtils;

public final class WaveQLExpression {

	public static Query applyExpression(EntityManager em, Map<String, String[]> expressions, String originalQuery, String expressao){
		Query query = null;
		if (isBlankExpression(expressao)){
			query = em.createQuery(originalQuery);
		}else{
			int corte = expressao.indexOf(":");
			String realExpressao = expressao.substring(0, corte+1);
			String valor  = expressao.substring(corte+1);
			String[] jpqlExpression = expressions.get(realExpressao);
			String jpqlQuery = originalQuery + jpqlExpression[1];
			query = em.createQuery(jpqlQuery);
			query.setParameter(jpqlExpression[0], "%"+valor.trim()+"%");
		}
		return query;
	}
	
	public static Query applyExpression(EntityManager em, String originalQuery, Map<String, Object[]> filters, String orderBy){
		
		StringBuilder queryBuilder = new StringBuilder(originalQuery);
		if (filters != null && !filters.isEmpty()){
			int ins = 0;
			for(String filter : filters.keySet()){
				queryBuilder.append(" and ");
				if (filter.startsWith("IN")){
					int inPos = filter.indexOf(")");
					String in = filter.substring(3, inPos+1);
					in = ", IN(t."+in+" r"+ins+" ";
					filter = "r"+ins+"."+filter.substring(inPos+1);
					ins++;
					int wherePos = queryBuilder.indexOf("where");
					String qry = queryBuilder.substring(0, wherePos);
					queryBuilder = new StringBuilder( qry + in + queryBuilder.substring(wherePos) );
					queryBuilder.append(filter);
				}else if (filter.startsWith("(")){
					//por enquanto nada, mas temos casos onde a query precisa de or por exemplo ((x == true) or (y == true)) nesses casos
					//nao queremos um t. antes do parentes, em contrapartida espera-se que o cliente da API use t.x e t.y
					queryBuilder.append(filter);
				}else{
					queryBuilder.append("t.");
					queryBuilder.append(filter);
				}	
			}
		}
		if (orderBy != null){
			queryBuilder.append(" order by t." + orderBy);
		}
		
		Query query = em.createQuery(queryBuilder.toString());
		//query.setParameter(1, CodEmpresaHolder.get());
		
		if (filters != null && !filters.isEmpty()){
			int i = 2;
			for(Object[] parameters : filters.values()){
				for(int x = 0, s = parameters.length; x < s; x++){
					query.setParameter(i, parameters[x]);
					i++;
				}
			}
		}
		return query;
	}
	
	
	private static boolean isBlankExpression(String expressao){
		boolean isBlank = false;
		if (!StringUtils.isBlank(expressao)){
			int corte = expressao.indexOf(":");
			if (corte < 0){
				isBlank = true;
			}else{
				String valor  = expressao.substring(corte+1);
				if (StringUtils.isBlank(valor.trim())){
					isBlank = true;
				}
			}
		}else{
			isBlank = true;
		}
		return isBlank;
	}

	
}
