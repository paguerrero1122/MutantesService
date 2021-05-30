package com.mercadolibre.mutants;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.mercadolibre.mutants.negocio.NegocioEstadistica;
import com.mercadolibre.mutants.negocio.NegocioMutantes;
import com.mercadolibre.mutants.services.ServicioMutantes;

@RunWith(SpringRunner.class)
@WebMvcTest(ServicioMutantes.class)
public class ServicioMutantesTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private NegocioMutantes negocioMutantes;
	
	@MockBean
	private NegocioEstadistica negocioEstadistica;
	
	@Test
	public void analizarDNA() throws Exception {
		mvc.perform(post("/serviceMutants/mutant").contentType(MediaType.APPLICATION_JSON).content("{\"dna\":[\"AAAAAA\",\"TTTTTT\"]}"))
					.andExpect(status().isForbidden());
	}
	
	@Test
	public void obtenerEstadisticas() throws Exception {
		mvc.perform(get("/serviceMutants/stats")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
	}
}
