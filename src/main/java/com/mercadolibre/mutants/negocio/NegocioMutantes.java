package com.mercadolibre.mutants.negocio;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mercadolibre.mutants.constantes.Constantes;
import com.mercadolibre.mutants.dto.DnaDTO;
import com.mercadolibre.mutants.dto.EstadisticaDTO;
import com.mercadolibre.mutants.dto.MutanteDTO;
import com.mercadolibre.mutants.exception.ChallengeException;
import com.mercadolibre.mutants.persistence.RedisRepository;

@Service
public class NegocioMutantes {

	private static final Logger logger = Logger.getLogger(NegocioMutantes.class);	
	
	@Autowired
	private RedisRepository<MutanteDTO, String> redisRepository;
	
	@Autowired
	private NegocioEstadistica negocioEstadistica;
	
	
	/**
	 * Metodo de negocio analiza , guarda el DNA analizado  el analisis lo realiza a traves de metodo isMutant(String[])
	 * una vez analizado el DNA los busca en Base de Redis y si existe omite volverlo a guardar
	 * pero si no existe lo guarda y actualiza la estadistica de numero de humanos , numero de mutantes y proporcion
	 * devuelve al servicio REST expuesto un true para mutante y un false para humano
	 * @param estadisticaDna
	 */
	public boolean identificarMutante(DnaDTO dna) {
		boolean esMutante;
		try {
			MutanteDTO dnaAnalizado = new MutanteDTO();
			esMutante = dnaAnalizado.isMutant(dna);
			MutanteDTO dnaEnBD = obtenerDNAAnalizado(dnaAnalizado);
			if(dnaEnBD == null) {
				insertarDNAAnalizado(dnaAnalizado);
				EstadisticaDTO estadistica = negocioEstadistica.obtenerEstadisticaDNA();
				if(estadistica == null) {
					estadistica = new EstadisticaDTO();
				}
				if (esMutante) {
					estadistica.setCantidadDnaMutantes(estadistica.getCantidadDnaMutantes()+1);
				}else {
					estadistica.setCantidadDnaHumanos(estadistica.getCantidadDnaHumanos()+1);
				}
				estadistica.calcularProporcion();
				negocioEstadistica.insertarEstatisticaDNA(estadistica);
			}
		} catch (Exception e) {
			String mensaje = "Ocurrio un error realizando la identificacion del DNA: "+e.getMessage();
			logger.error(mensaje);
			throw new ChallengeException(mensaje);
		}
		return esMutante;
	}
	
	/**
	 * Metodo de negocio que guarda y mantiene la lista de DNA Analizados 
	 * una ves inyectado RedisRepository hace uso del metodo que guarda objetos de tipo HASH en Redis
	 * guarda el objeto de DNA con una key que se encuentra en la constante Constantes.DNA_ANALIZADOS con 
	 * y value el objeto MutanteDTO
	 * @param estadisticaDna
	 */
	public void insertarDNAAnalizado(MutanteDTO dnaAnalizado) {
		try {
			redisRepository.saveHash(Constantes.DNA_ANALIZADOS, dnaAnalizado, dnaAnalizado.getCadenaADN());
		} catch (Exception e) {
			String mensaje = "Ocurrio un error insertando DNA en BD: "+e.getMessage();
			logger.error(mensaje);
			throw new ChallengeException(mensaje);
		}
	}
	
	/**
	 * Metodo de negocio que obtiene un objeto de tipo MutanteDTO , que esta alojado en Redis
	 * una ves inyectado RedisRepository hace uso del metodo que busca en Redis a traves de la key en el tipo HASH en Redis
	 * @return EstadisticaDTO
	 */
	public MutanteDTO obtenerDNAAnalizado(MutanteDTO mutante) {
		try {
			return redisRepository.findHashforKey(Constantes.DNA_ANALIZADOS, mutante.getCadenaADN());
		} catch (Exception e) {
			String mensaje = "Ocurrio un error al obtener DNA de BD: "+e.getMessage();
			logger.error(mensaje);
			throw new ChallengeException(mensaje);
		}
	}
	
	
}
