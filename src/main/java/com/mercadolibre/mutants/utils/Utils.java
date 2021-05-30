package com.mercadolibre.mutants.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	/**
	 * Metodo que valida si una cadena cumple con la expresion regular dada , para el caso de DNA Mutantes 
	 * @param regex
	 * @param cadena
	 * @return boolean
	 */
	public static boolean validarCadenaExpresionRegular(String regex , String cadena) {
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(cadena);
		return match.find();
	}
	
	/**
	 * Metodo que permite transponer una matriz (convertir filas a columnas)
	 * y retona un listado de String que representa las filas de la matriz
	 * @param cadenas
	 */
	public static String[] obtenerMatrizTranspuesta(String[] cadenas) {
		String matrizTraspuesta[] = new String[cadenas[0].length()];
		for (int i = 0; i < cadenas[0].length(); i++) {
			String cadenaHorizontal="";
			for (String cadena : cadenas) {
				cadenaHorizontal += cadena.charAt(i);
			}
			matrizTraspuesta[i] = cadenaHorizontal;
		}
		return matrizTraspuesta;
	}
	
	/**
	 * Metodo que convierte un String[] en una matriz cuadrada de NxN
	 * @param cadenas
	 * @return
	 */
	public static String[][] obtenerMatrizAtravesCandenas(String[] cadenas){
		String[][] matriz= null;
		if(cadenas.length > 0) {
			matriz = new String[cadenas.length][cadenas.length];
			for (int fila = 0; fila < cadenas.length; fila++) {
				for (int col = 0; col < cadenas[fila].length(); col++) {
					matriz[fila][col] = Character.toString(cadenas[fila].charAt(col));  
				}
			}
		}
		return matriz;
	}
	
	/**
	 * Metodo que devuelve un String[] con todas las diagonales de la una matriz
	 * @param matriz
	 * @return
	 */
	public static String[] obtenerDiagonalesMatriz(String[][] matriz) {
		List<String> diagonales = new ArrayList<String>();
		int fila=0;
		int column=0;
		String diagonal;
		for (int f = 0; f < matriz.length; f++) {
			fila = f;
			diagonal ="";
			while (fila >=0) {
				diagonal += matriz[fila][column];
				fila--;
                column++;
			}
			column =0;
			diagonales.add(diagonal);
		}
		fila = matriz.length-1;
		for (int c = 1; c < matriz.length; c++) {
			column = c;
			diagonal ="";
			while (column < matriz.length) {
				diagonal += matriz[fila][column];
	            fila--;
	            column++;
			}
			fila = matriz.length-1;
			diagonales.add(diagonal);
		}
		
		fila = 0;
		column = matriz.length-1;
		
		for (int f = 0; f < matriz.length; f++) {
			fila = f;
			diagonal ="";
			while (fila >=0) {
				diagonal += matriz[fila][column];
				fila--;
                column--;
			}
			column = matriz.length-1;
			diagonales.add(diagonal);
		}
		
		fila = matriz.length-1;
		
		for (int c = matriz.length-2; c >= 0; c--) {
			column = c;
			diagonal ="";
			while (column >= 0) {
				diagonal += matriz[fila][column];
	            fila--;
	            column--;
			}
			fila = matriz.length-1;
			diagonales.add(diagonal);
		}
		
		return diagonales.toArray(new String[diagonales.size()]);
	}
	
}
