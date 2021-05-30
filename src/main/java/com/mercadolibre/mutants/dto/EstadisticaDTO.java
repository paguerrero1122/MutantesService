package com.mercadolibre.mutants.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstadisticaDTO {

	
	private long cantidadDnaMutantes;
	private long cantidadDnaHumanos;
	private double proporcion;
	
	public EstadisticaDTO() {
		
	}

	@JsonProperty("count_mutant_dna")
	public long getCantidadDnaMutantes() {
		return cantidadDnaMutantes;
	}

	@JsonProperty("count_mutant_dna")
	public void setCantidadDnaMutantes(long cantidadDnaMutantes) {
		this.cantidadDnaMutantes = cantidadDnaMutantes;
	}

	@JsonProperty("count_human_dna")
	public long getCantidadDnaHumanos() {
		return cantidadDnaHumanos;
	}

	@JsonProperty("count_human_dna")
	public void setCantidadDnaHumanos(long cantidadDnaHumanos) {
		this.cantidadDnaHumanos = cantidadDnaHumanos;
	}

	@JsonProperty("ratio")
	public double getProporcion() {
		return proporcion;
	}

	@JsonProperty("ratio")
	public void setProporcion(double proporcion) {
		this.proporcion = proporcion;
	}
	
	/**
	 * Metodo que calcula la proporcion de mutantes en funcion de la cantidad de humanos
	 * este calculo se realiza en cada analisis de DNA con el fin de mantener actualizada la estadistica
	 * en la base de datos de Redis
	 */
	public void calcularProporcion() {
		if(cantidadDnaHumanos > 0) {
			this.setProporcion(new Double(cantidadDnaMutantes) / new Double(cantidadDnaHumanos));
		}
	}
	
}
