package com.jnj.wp.entity.dao;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.joda.time.DateTime;

import com.jnj.wp.entity.Entity;

/**
 * {@link EntityDateListener} performs the Date related manipulations to
 * {@link Entity} instance.This is actually used by JPA framework to perform pre
 * update/create operation logic.
 * 
 */
public class EntityDateListener {

	public EntityDateListener() {
		// Default Constructor.
	}

	@PreUpdate
	protected void onUpdate(Entity entity) {
		entity.setUpdateDate(DateTime.now());
	}

	@PrePersist
	protected void onCreate(Entity entity) {
		DateTime now = DateTime.now();
		entity.setCreateDate(now);
		entity.setUpdateDate(now);
	}
}
