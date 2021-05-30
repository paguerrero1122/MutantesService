package com.mercadolibre.mutants.dto;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.mutants.constantes.Constantes;
import com.mercadolibre.mutants.exception.ChallengeException;
import com.mercadolibre.mutants.utils.Utils;

public class MutanteDTO {
	
	private static final Logger logger = Logger.getLogger(MutanteDTO.class);
	
	private int numeroSecuenciasADN;
	private String cadenaADN;
	
	public MutanteDTO() {
		
	}

	@JsonProperty("numeroSecuenciasADN")
	public int getNumeroSecuenciasADN() {
		return numeroSecuenciasADN;
	}
	@JsonProperty("numeroSecuenciasADN")
	public void setNumeroSecuenciasADN(int numeroSecuenciasADN) {
		this.numeroSecuenciasADN = numeroSecuenciasADN;
	}
	@JsonProperty("cadenaADN")
	public String getCadenaADN() {
		return cadenaADN;
	}
	@JsonProperty("cadenaADN")
	public void setCadenaADN(String cadenaADN) {
		this.cadenaADN = cadenaADN;
	}

	
	/**
	 * Metodo que permite determinar si una cadena de DNA cumple con las condiciones mutantes
	 * La cadena recibida se comporta como una matriz cuadrada, por tal motivo es necesario verificar si existen
	 * coincidencias horizontales , verticales y diagonales con 4 letras seguidas iguales ej: AAAATC
	 * si encuentra mas de dos cadenas que cumplan con esta condicion el metodo devuelve true de lo contrario false
	 * primero realiza analisis horizontal (escenario mas sencillo) si en este analisis no encuentra 2 coincidencias
	 * empezara analisis vertical si este analisis sumado al anterior no encuentra 2 coincidencias
	 * empezara analisis diagonal (escenario mas complejo) si  si este analisis sumado a los 2 anteriores no encuentra 2 coincidencias
	 * devolveria false denotando que el DNA  es humano de lo contario true denotando mutante
	 * @param dna
	 * @return boolean si es mutante o no
	 */
	public boolean isMutant(DnaDTO dna) {
		try {
			cadenaADN = Arrays.toString(dna.getDna());
			validarDNAHorizontal(dna.getDna());
			if (numeroSecuenciasADN < Constantes.NUMERO_SECUENCIAS_DETERMINAR_MUTANTE) {
				validarDNAVertical(dna.getDna());
			}
			if (numeroSecuenciasADN < Constantes.NUMERO_SECUENCIAS_DETERMINAR_MUTANTE) {
				validarDNADiagonal(dna.getDna());
			}
		} catch (Exception e) {
			throw new ChallengeException(e.getMessage());
		}
		return getNumeroSecuenciasADN() >= Constantes.NUMERO_SECUENCIAS_DETERMINAR_MUTANTE;
	}
	
	/**
	 * Metodo que permite validar las coincidencias de la matriz de forma horizontal
	 * como dna es Array de String no sera necesario ordenar los elementos en una matriz de NXN
	 * el analisis se puede hacer por cada cadena del Array y equivaldra a hacer un analis horizontal
	 * de esta forma se itera los elementos del Array si se pasan por una expresion Regular que determina si hay coincidencias
	 * de 4 letras iguales seguidas por cadena. ej: CCCCTG
	 * @param dna
	 */
	private void validarDNAHorizontal(String[] dna) {
		try {
			for (String cadenaDNA : dna) {
				if(cadenaDNA.length() >= Constantes.CONCIDENCIA_LETRAS_DNA) {
					if (Utils.validarCadenaExpresionRegular(Constantes.REGEX_DNA_MUTANT, cadenaDNA)) {
						numeroSecuenciasADN++;
						if (Constantes.NUMERO_SECUENCIAS_DETERMINAR_MUTANTE == numeroSecuenciasADN) {
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			String mensaje = "Ocurrio un errror analizando DNA de forma horizontal: "+e.getMessage();
			logger.error(e);
			throw new ChallengeException(mensaje);
		}
	}
	
	/**
	 * Metodo que permite validar las coincidencias de la matriz de forma vertical
	 * como dna es Array de String si se puede representar como una matriz de NxN , es necesario transponer la matriz
	 * convertir filas a columnnas de esta forma las concidencias que estaban de forma vertical se convierten en coincidencias horizontales
	 * de esta forma se puede utilizar el metodo validarDNAHorizontal para validar las coincidencias verticales como si fueran horizontales
	 * y optimiza la reutilizacion de codigo.
	 * @param dna
	 */
	private void validarDNAVertical(String[] dna) {
		try {
			String [] dnaMatrizTraspuesta = Utils.obtenerMatrizTranspuesta(dna);
			validarDNAHorizontal(dnaMatrizTraspuesta);
		} catch (Exception e) {
			String mensaje = "Ocurrio un errror analizando DNA de forma vertical: "+e.getMessage();
			logger.error(e);
			throw new ChallengeException(mensaje);
		}
	}
	
	/**
	 * Metodo que permite validar las coincidencias de la matriz de forma diagonal
	 * como dna es Array de String se debe representar en una Matriz de NxN (unicamente matriz cuadrada)
	 * para ello es necesario recorrer la matriz y obtener las cadenas de forma diagonal obteniendo el listado de diagonales de la matriz
	 * podemos utilizar el metodo validarDNAHorizontal que itera cada cadena y valida si existen coincidencias
	 * Las diagonales de matriz se obtienen tanto desde el punto 0,0 hasta ultima fila , ultima columna , como desde 0,ultima columna hasta ultima fila,0
	 * @param dna
	 */
	private void validarDNADiagonal(String[] dna) {
		try {
			String[][] matrizDNA = Utils.obtenerMatrizAtravesCandenas(dna);
			validarDNAHorizontal(Utils.obtenerDiagonalesMatriz(matrizDNA));
		} catch (Exception e) {
			String mensaje = "Ocurrio un errror analizando DNA de forma diagonal: "+e.getMessage();
			logger.error(e);
			throw new ChallengeException(mensaje);
		}
	}
}
