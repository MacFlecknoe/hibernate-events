package eventing;

import static org.junit.Assert.*;

import java.util.UUID;

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
	
	@Test
	public void testDaoInjection() {
		assertNotNull(fooDao);
	}
	
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testFooBuildFailure() {
		
		Foo.Builder builder = new Foo.Builder();
		
		builder.setId(UUID.randomUUID());
		builder.build();
	}

	@Test
	public void testEntityDateListener() {
		
		Foo.Builder builder = new Foo.Builder();
		
		builder.setName("mike");
		builder.setDescription("my name is michael lambert");
		builder.setId(UUID.randomUUID());
		
		Foo foo = builder.build();
		
		assertNull(foo.getCreateDate()); // nothing up my sleeve
		
		foo.create();
		
		Foo retreivedFoo = fooDao.findById(foo.getId());
		
		assertNotNull(retreivedFoo);
		assertNotNull(retreivedFoo.getCreateDate()); // attribute inserted by listener
	}
}
