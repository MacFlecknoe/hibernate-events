package com.jnj.wp.entity.dao;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jnj.wp.entity.Foo;

public class FooJpaDao extends EntityJpaDao<Foo> implements IFooDao {
	
	public FooJpaDao(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Foo findById(UUID id) {
		return this.getEntityManager().find(Foo.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Foo> findByName(String name) {
		
		Query q = this.getEntityManager().createQuery("SELECT f FROM Foo f WHERE f.name = ?");
		q.setParameter(1, name);
		
		return (Collection<Foo>) q.getResultList();
	}

}
