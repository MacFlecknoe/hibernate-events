package com.jnj.wp.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.type.UUIDBinaryType;
import org.jadira.usertype.dateandtime.joda.PersistentInterval;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.jnj.wp.entity.dao.EntityDaoRegistry;
import com.jnj.wp.entity.dao.EntityDateListener;
import com.jnj.wp.entity.dao.IEntityDao;

/**
 * Base abstract class for all entities. Entity classes will ordinarily have a
 * DAO associated with them and will be exposed through a service. Entity
 * objects test equivalency by comparing identifiers rather than state.
 */
@MappedSuperclass
@TypeDefs({
	@TypeDef(name = "jodaDateTimeType", typeClass = org.jadira.usertype.dateandtime.joda.PersistentDateTime.class, defaultForType = DateTime.class),
	@TypeDef(name = "uuidType", typeClass = UUIDBinaryType.class),
	@TypeDef(name = "jodaIntervalType", typeClass = PersistentInterval.class, defaultForType = Interval.class) 
})
@Access(value = AccessType.FIELD)
@EntityListeners(EntityDateListener.class)
public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Type(type = "uuidType")
	@Column(length = 16)
	private final UUID id;
	@Version
	private int version;
	@Type(type = "jodaDateTimeType")
	@Column(updatable = false)
	private DateTime createDate;
	@Type(type = "jodaDateTimeType")
	private DateTime updateDate;

	/**
	 * Provide a default protected constructor to allow the Hibernate framework
	 * to create instances of this class using the Reflection API Do not use
	 * this for subclass constructor invocation.
	 * 
	 * @see <a href="http://stackoverflow.com/a/3683453/791833">http://stackoverflow.com/a/3683453/791833</a>
	 */
	protected Entity() {
		// Initialize id and Create date.
		this.id = null;
		this.createDate = null;
	}

	protected Entity(IEntityBuilder<? extends Entity> builder) {
		if (builder == null) {
			throw new IllegalArgumentException("Invalid Instantiation");
		}
		this.id = builder.getId();
		this.createDate = builder.getCreateDate();
		this.updateDate = builder.getUpdateDate();
		this.version = builder.getVersion();
	}

	public UUID getId() {
		return id;
	}

	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}

	public DateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		if (version < 0) {
			throw new IllegalArgumentException("Version cannot be less than 0.");
		}
		this.version = version;
	}

	/**
	 * <p>
	 * This method checks the equality between two instances by comparing the
	 * business key. Subclasses overriding this method must call
	 * <code>super.equals</code>.
	 * </p>
	 * <p>
	 * Hibernate documentation recommend's implementing <code>equals</code> and
	 * <code>hashCode</code> using business key equality.
	 * </p>
	 * 
	 * @see http
	 *      ://docs.redhat.com/docs/en-US/JBoss_Enterprise_Application_Platform
	 *      /4.2/html/Hibernate_Reference_Guide/Persistent_Classes-
	 *      Implementing_equals_and_hashCode.html
	 */
	@Override
	public boolean equals(Object object) {

		if (object == this) {
			return true;
		}
		if (!(object instanceof Entity)) {
			return false;
		}
		Entity entity = (Entity) object;

		EqualsBuilder isEquals = new EqualsBuilder();
		isEquals.append(getId(), entity.getId());

		return isEquals.isEquals();
	}

	/**
	 * This method creates a hash code based upon the business key. Subclasses
	 * should not override this method.
	 * 
	 * @see http://docs.redhat.com/docs/en-US/JBoss_Enterprise_Application_Platform/4.2/html/Hibernate_Reference_Guide/Persistent_Classes-Implementing_equals_and_hashCode.html
	 */
	@Override
	public int hashCode() {

		HashCodeBuilder hashBuilder = new HashCodeBuilder(71, 283);
		hashBuilder.append(getId());

		return hashBuilder.toHashCode();
	}

	/**
	 * creates/persists this instance to db using the appropriate dao instance.
	 */
	@SuppressWarnings(value = "unchecked")
	public <E extends Entity> void create() {
		IEntityDao<E> entityDao = (IEntityDao<E>) EntityDaoRegistry.getInstance().getDao(this.getClass());
		entityDao.create((E) this);
	}

	/**
	 * updates this instance to db using the appropriate dao instance.
	 * 
	 * @return updated entity.
	 */
	@SuppressWarnings(value = "unchecked")
	public <E extends Entity> E update() {
		IEntityDao<E> entityDao = (IEntityDao<E>) EntityDaoRegistry.getInstance().getDao(this.getClass());
		return entityDao.update((E) this);
	}

	/**
	 * deletes this instance from db.
	 */
	@SuppressWarnings(value = "unchecked")
	public <E extends Entity> void delete() {
		IEntityDao<E> entityDao = (IEntityDao<E>) EntityDaoRegistry.getInstance().getDao(this.getClass());
		entityDao.delete((E) this);
	}

	public static interface IEntityBuilder<T extends Entity> {
		public UUID getId();
		public DateTime getCreateDate();
		public DateTime getUpdateDate();
		public int getVersion();
	}
}