package com.mercadolibre.mutants.constantes;

public class Constantes {

	public static final int NUMERO_SECUENCIAS_DETERMINAR_MUTANTE = 2;
	public static final int CONCIDENCIA_LETRAS_DNA = 4;
	public static final String REGEX_DNA_MUTANT = "[A]{4,4}|[C]{4,4}|[G]{4,4}|[T]{4,4}";
	public static final String DNA_ANALIZADOS= "DNA_ANALIZADOS";
	public static final String DNA_ESTADISTICAS= "DNA_ESTADISTICAS";
	public static final String REDIS_SERVER="spring.redis.host";
	public static final String REDIS_SERVER_PORT="spring.redis.port";
}
