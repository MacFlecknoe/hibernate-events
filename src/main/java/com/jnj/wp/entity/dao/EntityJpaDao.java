package com.jnj.wp.entity.dao;

import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.jnj.wp.entity.Entity;

/**
 * Base data access object responsible for CRUD operations
 * 
 * @param <T> represents entity
 */
public abstract class EntityJpaDao<T extends Entity> implements IEntityDao<T> {

	private final EntityManager entityManager;

	public EntityJpaDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Transactional
	@Override
	public void create(T entity) {
		entityManager.persist(entity);
	}
	
	@Transactional
	@Override
	public T update(T entity) {
		
		entityManager.detach(entity);
		T result = entityManager.merge(entity);
		entityManager.flush();
		
		return result;
	}

	@Transactional
	@Override
	public void delete(T entity) {
		entityManager.remove(entity);
	}
	
	public abstract T findById(UUID id);
}