package com.mercadolibre.mutants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.mutants.constantes.Constantes;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class ChallengeMutantsConfig {
	
	private static final Logger logger = Logger.getLogger(ChallengeMutantsConfig.class); 

	@Autowired
	Environment env;
	
	/**
	 * Bean que se configura al momento de levantar la aplicación
	 * Este Bean configura la conexion y un pool para la interacción con una base de datos NoSQL Redis
	 * @return
	 */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(env.getProperty(Constantes.REDIS_SERVER));
        config.setPort(Integer.parseInt(env.getProperty(Constantes.REDIS_SERVER_PORT)));
		JedisConnectionFactory jedisFactory = new JedisConnectionFactory(config);
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	    jedisPoolConfig.setMaxTotal(2048);
	    jedisPoolConfig.setMaxIdle(200);
	    jedisPoolConfig.setMinIdle(2);
		jedisFactory.setPoolConfig(jedisPoolConfig);
		logger.info("Conexion establecida con Redis");
		return jedisFactory;
	}
	
	

	/**
	 * Bean que se configura al levantar la aplicación
	 * Este Bean configura RedisTemplate , un objeto que se utiliza para realizar las operaciones necesarias a traves de Redis
	 * dicho objeto se configura con la conexión ya establecida en el metodo jedisConnectionFactory()
	 * y configura la forma de serializar y desealizar los valores enviados a Redis , para este caso serializando de Object a JSON
	 * y deserializando de JSON a Object
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> getRedisTemplate(){
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		RedisSerializer stringRedisSerializar = new StringRedisSerializer();
		Jackson2JsonRedisSerializer jacksonSerializer= new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jacksonSerializer.setObjectMapper(objectMapper);
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setKeySerializer(stringRedisSerializar);
	    redisTemplate.setValueSerializer(jacksonSerializer);
	    redisTemplate.setHashKeySerializer(stringRedisSerializar);
	    redisTemplate.setHashValueSerializer(jacksonSerializer);
	    redisTemplate.setEnableTransactionSupport(true);
	    logger.info("RedisTemplate Inicializado de forma correcta");
		return redisTemplate;
	}
}
