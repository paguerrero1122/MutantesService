package com.mercadolibre.mutants.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DnaDTO {
	
	private String dna[];
	
	public DnaDTO() {
		
	}

	@JsonProperty("dna")
	public String[] getDna() {
		return dna;
	}

	@JsonProperty("dna")
	public void setDna(String[] dna) {
		this.dna = dna;
	}
	
	
}
