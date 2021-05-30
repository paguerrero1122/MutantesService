package com.mercadolibre.mutants.persistence;

import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository<T,ID> implements IRedisRepository<T, ID> {
	
	private HashOperations<String, String, T> hashOperations;
	
	public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
		hashOperations = redisTemplate.opsForHash();
	}
	
	
	/**
	 * Metodo que guarda un hash en Redis
	 */
	@Override
	public void saveHash(String key , T object , ID atributo){
		hashOperations.put(key, atributo.toString() , object);
	}

	/**
	 * Metodo que busca una lista de hash de acuerdo a la key proporcionada
	 */
	@Override
	public Map<String, T> findAllHash(String key) {
		return hashOperations.entries(key);
	}
	
	/**
	 * Metodo que guarda un map con n cantidad de hash en Redis
	 */
	@Override
	public void saveListHash(String key , Map<String, T> hashes) {
		hashOperations.putAll(key.toString(), hashes);
	}
	
	/**
	 * Metodo que busca un  hash de acuerdo a la key proporcionada y al atributo proporcionado
	 */
	@Override
	public T findHashforKey(String key ,String atributo) {
		T result = hashOperations.get(key, atributo);
		return result;
	}
	

}
