package action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;

import model.Person;

public class SignupActionTest extends AbstractDataTest {

	SignupAction action;
	PersonHome personHome;
	Person person;

	final static String KNOWN_EMAIL = "ruffian42@a.b.co", KNOWN_USERNAME="the id";

	@Before
	public void readySteadyGo() {
		personHome = new PersonHome();
		personHome.em = em;
		action = new SignupAction();
		action.personHome = personHome;
		action.entityManager = em;
		
		action.setFirstName("Robin");
		action.setLastName("Smith");
		action.setEmail(KNOWN_EMAIL);
		action.setUserName(KNOWN_USERNAME);
		personHome.getInstance().setAcceptedLicense(true);
		personHome.getInstance().setMinimumAge(true);
		final EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		action.save();
		transaction.commit();
	}

	@Test
	public void testUserInUse() {
		System.out.println("SignupActionTest.testUserInUse()");
		action.setUserName(KNOWN_USERNAME);
		assertTrue("user in use", action.isUserNameInUse());
	}
	
	@Test
	public void testUserNotInUse() {
		System.out.println("SignupActionTest.testUserNotInUse()");
		action.setUserName("random_user_42");
		assertFalse("user not in use", action.isUserNameInUse());
	}
	
	@Test
	public void testEmailInUse() throws Exception {
		System.out.println("SignupActionTest.testEmailInUse()");
		action.setEmail(KNOWN_EMAIL);
		assertTrue("email in use", action.isEmailInUse());
	}
	
	@Test
	public void testEmailNotInUse() throws Exception {
		System.out.println("SignupActionTest.testEmailNotInUse()");
		action.setEmail("random_email@example.com");
		assertFalse("email available", action.isEmailInUse());
	}
}
