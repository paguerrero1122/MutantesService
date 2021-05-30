package com.mercadolibre.mutants;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@AutoConfigureMockMvc
public class ServicioMutanteE2CTest {

	
	@Autowired
	private MockMvc mvc;
	
	
	@Test
	public void analizarDNAMutante() throws Exception {
		mvc.perform(post("/serviceMutants/mutant").contentType(MediaType.APPLICATION_JSON).content("{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}"))
					.andExpect(status().isOk());
	}
	
	@Test
	public void analizarDNAMHumano() throws Exception {
		mvc.perform(post("/serviceMutants/mutant").contentType(MediaType.APPLICATION_JSON).content("{\"dna\":[\"TTGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAACG\",\"CCTCTA\",\"TCACTG\"]}"))
					.andExpect(status().isForbidden());
	}
	
	@Test
	public void obtenerEstadisticas() throws Exception {
		ResultActions resultActions = mvc.perform(get("/serviceMutants/stats")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		System.out.println(contentAsString);
	}
	
	
}
