package action;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class AbstractDataTest {
	static EntityManagerFactory emf;
	EntityManager em;
	
	@BeforeClass
	public static void beforeClass() {
		emf = Persistence.createEntityManagerFactory("todolist");		
	}
	
	@Before
	public void before() {
		em = emf.createEntityManager();		
	}
	
	@After
	public void after() {
		em.close();
	}
	
	@AfterClass
	public static void afterClass() {
		emf.close();
	}
}
