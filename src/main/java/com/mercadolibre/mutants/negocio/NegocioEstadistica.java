package com.mercadolibre.mutants.negocio;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.mutants.constantes.Constantes;
import com.mercadolibre.mutants.dto.EstadisticaDTO;
import com.mercadolibre.mutants.exception.ChallengeException;
import com.mercadolibre.mutants.persistence.RedisRepository;

@Service
public class NegocioEstadistica {
	
	private static final Logger logger = Logger.getLogger(NegocioEstadistica.class);

	@Autowired
	private RedisRepository<EstadisticaDTO, String> redisRepository;
	
	
	
	/**
	 * Metodo de negocio que guarda y mantiene la estadistica de mutantes humanos y su proporcion
	 * una ves inyectado RedisRepository hace uso del metodo que guarda objetos de tipo HASH en Redis
	 * guarda el objeto de estadisticas con una key que se encuentra en la constante Constantes.DNA_ESTADISTICAS con 
	 * y value el objeto EstadisticaDTO
	 * @param estadisticaDna
	 */
	public void insertarEstatisticaDNA(EstadisticaDTO estadisticaDna) {
		try {
			redisRepository.saveHash(Constantes.DNA_ESTADISTICAS, estadisticaDna, Constantes.DNA_ESTADISTICAS);
		} catch (Exception e) {
			String mensaje = "Ocurrio un error insertando estadisticas del DNA: "+e.getMessage();
			logger.error(mensaje);
			throw new ChallengeException(mensaje);
		}
	}
	
	/**
	 * Metodo de negocio que obtiene un objeto de tipo EstadisticaDTO , que esta alojado en Redis
	 * una ves inyectado RedisRepository hace uso del metodo que busca en Redis a traves de la key en el tipo HASH en Redis
	 * @return EstadisticaDTO
	 */
	public EstadisticaDTO obtenerEstadisticaDNA() {
		try {
			return redisRepository.findHashforKey(Constantes.DNA_ESTADISTICAS,Constantes.DNA_ESTADISTICAS);
		} catch (Exception e) {
			String mensaje = "Ocurrio un error obteniendo estadisticas del DNA: "+e.getMessage();
			logger.error(mensaje);
			throw new ChallengeException(mensaje);
		}
	}
}
