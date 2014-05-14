package com.jnj.wp.entity;

import java.util.UUID;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.joda.time.DateTime;

@javax.persistence.Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Foo extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	private final String name;
	private final String description;
	
	public Foo(Foo.Builder builder) {
		super(builder);
		this.name = builder.getName();
		this.description = builder.getDescription();
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public static class Builder implements IEntityBuilder<Foo> {
		
		private UUID id;
		private String name;
		private String description;
		private int version;
		private DateTime createDate;
		private DateTime updateDate;
		
		public UUID getId() {
			return id;
		}
		
		public Builder setId(UUID id) {
			this.id = id;
			return this;
		}
		
		public String getName() {
			return name;
		}
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
		public String getDescription() {
			return description;
		}
		
		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public int getVersion() {
			return version;
		}

		public Builder setVersion(int version) {
			this.version = version;
			return this;
		}

		@Override
		public DateTime getCreateDate() {
			return createDate;
		}
		
		public Builder setCreateDate(DateTime createDate) {
			this.createDate = createDate;
			return this;
		}

		@Override
		public DateTime getUpdateDate() {
			return updateDate;
		}
		
		public Builder setUpdateDate(DateTime updateDate) {
			this.updateDate = updateDate;
			return this;
		}
		
		public Foo build() {
			return new Foo(this);
		}
	}
}
