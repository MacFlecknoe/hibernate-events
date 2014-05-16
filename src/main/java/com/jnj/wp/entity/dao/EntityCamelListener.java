package com.jnj.wp.entity.dao;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.joda.time.DateTime;

import com.jnj.wp.entity.Entity;

public class EntityCamelListener {
	
	public EntityCamelListener() {
		// empty constructor
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
