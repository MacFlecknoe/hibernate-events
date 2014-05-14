package eventing;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.jnj.wp.entity.Foo;
import com.jnj.wp.entity.dao.IFooDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class FooJpaDaoTest {

	@Autowired
	private IFooDao fooDao;

	@Autowired
	private EntityManager entityManager;
	
	@Test
	public void testDaoInjection() {
		assertNotNull(fooDao);
	}

	@Test
	public void testFooCreate() {
		
		Foo.Builder builder = new Foo.Builder();
		
		builder.setName("mike");
		builder.setDescription("my name is");
		builder.setId(UUID.randomUUID());
		
		Foo foo = builder.build();
		
		foo.create();
		
		Foo retreivedFoo = fooDao.findById(foo.getId());
		
		assertNotNull(retreivedFoo);
		assertNotNull(retreivedFoo.getCreateDate()); // attribute inserted by listener
	}
}
