package com.jnj.wp.entity.dao;

import java.util.Collection;

import com.jnj.wp.entity.Foo;

public interface IFooDao extends IEntityDao<Foo> {

	public Collection<Foo> findByName(String name);
}
