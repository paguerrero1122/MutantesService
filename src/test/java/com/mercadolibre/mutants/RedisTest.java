package com.mercadolibre.mutants;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mercadolibre.mutants.persistence.RedisRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
public class RedisTest {

	@Autowired
	private RedisRepository<String, String> redisRepository;
	
	@Test
	public void insertarHashRedis() {
		redisRepository.saveHash("INSERT REDIS", "HOLA MUNDO", "SALUDO");
	}
	
	@Test
	public void obtenerHashRedis() {
		String hash = redisRepository.findHashforKey("INSERT REDIS", "SALUDO");
		assertNotNull(hash);
	}
	
	@Test
	public void obtenerListadoDeHashRedis() {
		Map<String, String> hashs = redisRepository.findAllHash("INSERT REDIS");
		assertNotNull(hashs);
	}
	
	@Test
	public void guardarGrupoHashRedis() {
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("HOLA1", "HOLA1");
		hash.put("HOLA2", "HOLA2");
		redisRepository.saveListHash("INSERT REDIS", hash);
	}
}
