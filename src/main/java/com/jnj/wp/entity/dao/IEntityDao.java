package com.jnj.wp.entity.dao;

import java.util.UUID;

import com.jnj.wp.entity.Entity;

public interface IEntityDao<T extends Entity> {

	public void create(T entity);
	
	public T update(T entity);
	
	public void delete(T entity);
	
	public T findById(UUID id);
}
