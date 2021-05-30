package com.mercadolibre.mutants.persistence;

import java.util.Map;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IRedisRepository<T , ID> {

	public void saveHash(String key , T object , ID atributo);
	
	public Map<String,T> findAllHash(String key);
	
	public void saveListHash(String key , Map<String, T> hashes);
	
	public T findHashforKey(String key ,String atributo);
	
}
