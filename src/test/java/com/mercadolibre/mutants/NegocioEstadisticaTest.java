package com.mercadolibre.mutants;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.mercadolibre.mutants.dto.EstadisticaDTO;
import com.mercadolibre.mutants.negocio.NegocioEstadistica;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
public class NegocioEstadisticaTest {

	
	@TestConfiguration
	static class NegocioEstadisticaTestBean{
		@Bean
		public NegocioEstadistica getNegocioEstadistica() {
			return new NegocioEstadistica();
		}
	}
	
	@Autowired
	private NegocioEstadistica negocioEstadistica;
	
	@Test
	public void ingresarEstadisticaDB() {
		EstadisticaDTO estadistica = new EstadisticaDTO();
		estadistica.setCantidadDnaHumanos(4);
		estadistica.setCantidadDnaMutantes(20);
		estadistica.calcularProporcion();
		negocioEstadistica.insertarEstatisticaDNA(estadistica);
	}
	
	@Test
	public void obtenerEstadisticaDB() {
		EstadisticaDTO estadistica = new EstadisticaDTO();
		estadistica = negocioEstadistica.obtenerEstadisticaDNA();
		assertNotNull(estadistica);
	}
}
