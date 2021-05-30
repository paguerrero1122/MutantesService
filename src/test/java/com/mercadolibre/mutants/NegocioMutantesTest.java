package com.mercadolibre.mutants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.mercadolibre.mutants.dto.DnaDTO;
import com.mercadolibre.mutants.dto.MutanteDTO;
import com.mercadolibre.mutants.negocio.NegocioMutantes;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
public class NegocioMutantesTest {

	@TestConfiguration
	static class NegocioMutantesTestBean{
		@Bean
		public NegocioMutantes getNegocioMutantes() {
			return new NegocioMutantes();
		}
	}
	
	@Autowired
	private NegocioMutantes negocioMutantes;
	
	
	@Test
	public void identificarMutante() {
		DnaDTO dna = new DnaDTO();
		String[] cadenaDNA = new String[] {"AAAAA","CCCCC"};
		dna.setDna(cadenaDNA);
		boolean esMutante = negocioMutantes.identificarMutante(dna);
	}
	
	@Test
	public void guardarCadenaDNA() {
		MutanteDTO mutante = new MutanteDTO();
		mutante.setCadenaADN("[\"AAAA\",\"TTTTG\"]");
		mutante.setNumeroSecuenciasADN(2);
		negocioMutantes.insertarDNAAnalizado(mutante);
	}
	
	@Test
	public void obtenerCadenaDNA() {
		MutanteDTO mutante = new MutanteDTO();
		mutante.setCadenaADN("[\"AAAA\",\"TTTTT\"]");
		mutante.setNumeroSecuenciasADN(2);
		negocioMutantes.obtenerDNAAnalizado(mutante);
	}
	
}
