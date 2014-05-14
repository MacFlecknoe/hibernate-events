package com.jnj.wp.entity.dao;

import java.util.Map;

import com.jnj.wp.entity.Entity;

/**
 * Registry of DAO's extending {@link IEntityDao}
 * 
 */
public class EntityDaoRegistry {

	private static volatile EntityDaoRegistry INSTANCE;

	private final Map<Class<? extends Entity>, IEntityDao<? extends Entity>> daoRegistry;

	private EntityDaoRegistry(Map<Class<? extends Entity>, IEntityDao<? extends Entity>> daoRegistry) {
		this.daoRegistry = daoRegistry;
		INSTANCE = this;
	}

	public synchronized static EntityDaoRegistry getInstance() {
		if (INSTANCE == null) {
			throw new IllegalStateException("instance has not been initialized");
		}
		return INSTANCE;
	}

	public synchronized static void setInstance(EntityDaoRegistry registry) {
		if (INSTANCE != null) {
			throw new IllegalStateException("instance already initialized");
		}
		INSTANCE = registry;
	}

	/**
	 * getDao method returns a DAO specific to the entity class supplied as
	 * argument (provided DAO's are registered via 'setInstance' method)
	 * 
	 * @param entityClazz
	 *            {@link Class} of {@link Entity} type
	 * @return extends {@link IEntityDao<Entity>}
	 * 
	 *         Example: {@link IEmailAddressEntityDao} emailAddressEntityDao =
	 *         EntityDaoRegistry.getInstance().getDao(EmailAddressEntity.class)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Entity, D extends IEntityDao<T>> D getDao(Class<T> entityClazz) {
		return (D) this.daoRegistry.get(entityClazz);
	}
}
