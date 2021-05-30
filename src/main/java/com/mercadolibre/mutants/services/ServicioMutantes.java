package com.mercadolibre.mutants.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.mutants.dto.DnaDTO;
import com.mercadolibre.mutants.dto.EstadisticaDTO;
import com.mercadolibre.mutants.negocio.NegocioEstadistica;
import com.mercadolibre.mutants.negocio.NegocioMutantes;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/serviceMutants")
public class ServicioMutantes {
	
	private static final Logger logger = Logger.getLogger(ServicioMutantes.class);
	
	@Autowired
	private NegocioMutantes negocioMutantes;
	@Autowired
	private NegocioEstadistica negocioEstadistica;

	
	/**
	 * Metodo que expone un servicio tipo POST para el analisis de DNA 
	 * @param dna Array de Strings cada String representa una cadena de DNA y que ademas se puede representar como una matriz cuadrada
	 * @return JSON HTTP 200 si la cadena ingresada cumple con las condiciones mutantes y HTTP 403 si no cumple
	 */
	@PostMapping("/mutant")
	public ResponseEntity<String> identificarMutante(@RequestBody DnaDTO dna){
		logger.info("Ingresando al servicio de identificacion de mutantes !!");
		try {
			boolean esMutante = negocioMutantes.identificarMutante(dna);
			if (esMutante) {
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("Mutante");
			}else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body("Opps es humano !!");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(e.getMessage());
		}
	}
	
	
	/**
	 * Metodo que expone servicio tipo GET para obtener las estadisticas de DNA analizados 
	 * conteo de mutantes y humanos y la proporcion de mutantes en relacion a los humanos 
	 * @return JSON EstadisticaDTO conteno mutantes , conteno de humanos y proporcion
	 */
	@GetMapping("/stats")
	public ResponseEntity<EstadisticaDTO> getEstadisticasMutantes(){
		EstadisticaDTO estadistica = negocioEstadistica.obtenerEstadisticaDNA();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(estadistica);
	}
}
