package action;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;

import model.Person;

public class PersonHomeTest extends AbstractDataTest {

	PersonHome home;
	Person person;

	final static String KNOWN_EMAIL = "ruffian42", KNOWN_USERNAME = "the id";

	@Before
	public void setup() {
		home = new PersonHome();
		home.em = em;
		person = new Person("Tom", "Dick", "Harry", KNOWN_EMAIL);
		person.setUserName(KNOWN_USERNAME);
		home.setInstance(person);
	}

	@Test
	public void testGetInstance() {
		final EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		home.save();
		transaction.commit();
		
		long id = person.getId();
		
		home.wire(id);
		
		Person p2 = home.getInstance();
		assertEquals(person, p2);
	}
	
	@Test
	public void testSaveNewPerson() throws Exception {
		final Person p = new Person("Test", "Tube", "User", KNOWN_EMAIL);
		p.setUserName("fredzz");
		home.setInstance(p);
		home.save();

		Person p2 = em.find(Person.class, home.getInstance().getId());
		assertEquals(p, p2);
	}
}
