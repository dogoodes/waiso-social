package com.waiso.social.framework.utilitario;

import java.util.ArrayList;

public class DigitoVerificador {

	
	public static int calculaDV(String numero) {
		int dv = new DigitoVerificador().new mod11().calculaDV(numero);
		return dv;
	}
	
	public static int calculaDVMod10(String numero) {
		int dv = new DigitoVerificador().new mod10().calculaDV(numero);
		return dv;
	}

	public static int calculaDVMod11(String numero) {
		int dv = new DigitoVerificador().new mod11().calculaDV(numero);
		return dv;
	}
	
	private final class mod10{
		public int calculaDV(String numero){
			int soma = calculaSomaMultiplicacao(numero);
			int restoDiv = (soma % 10);
			int dv = 10 - restoDiv;
			return dv;
		}
		
		private int calculaSomaMultiplicacao(String numero){
			int soma = 0;
			for (int i = 0; i < numero.length(); i++) {
				int valor = Character.getNumericValue(numero.charAt(i))
						* convertePosPeso(i + 1, numero.length());
				if (valor > 9){
					valor = calculaSomaDV(""+valor);
				}
				soma += valor;
			}
			return soma;
		}
		
		private int convertePosPeso(int posicao, int comprimento) {
			return (((comprimento - posicao)%2) ==0)?2:1;
		}
		
		private int calculaSomaDV(String numero) {
			int soma = 0;
			for (int i = 0; i < numero.length(); i++) {
				soma += Character.getNumericValue(numero.charAt(i));
			}
			return soma;
		}
	}
	
	
	private final class mod11{
		public int calculaDV(String numero) {
			int dv = 0;
			int soma = calculaSomaDV(numero);
			int restoDiv = (soma % 11);
			if (restoDiv > 1){
				dv = 11 - restoDiv;
			}
			return dv;
		}

		private int calculaSomaDV(String numero) {
			int soma = 0;
			for (int i = 0; i < numero.length(); i++) {
				soma += Character.getNumericValue(numero.charAt(i))
						* convertePosPeso(i + 1, numero.length());
			}
			return soma;
		}

		private int convertePosPeso(int posicao, int comprimento) {
			return ((comprimento - posicao) % 8) + 2;
		}
	}
	
	public static void main(String argv[]){
		System.out.println("--------------- Calculo MOD 10 ---------------");
		System.out.println(DigitoVerificador.calculaDVMod10("261533"));
		System.out.println("----------------------------------------------");
		
		System.out.println("--------------- Calculo MOD 11 ---------------");
		ArrayList<String> chaves = new ArrayList<String>();
		chaves.add("3511010896500100018255001000000549880126079"); //1
		chaves.add("3510090095764000019455001000000015745781372"); //0
		chaves.add("3510100095764000019455002000000096940893183"); //0
		chaves.add("3510090095764000019455002000000083445630235"); //0
		chaves.add("3510080545682800017255001000000007937405809"); //2
		chaves.add("3510080545682800017255001000000004595833847"); //3
		chaves.add("3510090545682800017255001000000014827685759"); //4
		chaves.add("3510090545682800017255001000000016828040722"); //5
		chaves.add("3510080545682800017255001000000006936050430"); //6
		chaves.add("3510090545682800017255001000000019829746874"); //7
		chaves.add("3510080545682800017255001000000002488093224"); //8
		chaves.add("3510080545682800017255001000000005935288994"); //9
		for(String chave: chaves){
			System.out.println(DigitoVerificador.calculaDV(chave));
		}
		System.out.println("----------------------------------------------");
	}
}
