package action;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Person;
import com.darwinsys.security.DigestUtils;
import com.darwinsys.security.PassPhrase;
import jsf.JsfUtil;

@Named("signupAction")
@ViewScoped
public class SignupAction implements Serializable {

	private static final long serialVersionUID = 12983920811890L;

	@PersistenceContext EntityManager entityManager;

	@Inject PersonHome personHome;

	final static Logger logger = Logger.getLogger(SignupAction.class.getName());

	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	
	private static final String[][] BLOCKED_EMAILS = {
		// row[0] Email in full  row[1] Accompanying snarky message
		{ "billg@microsoft.com", "Wouldn't you rather use Windows'95?" },
		{ "sjobs@apple.com",     "Just get an iPhone off the barroom floor" }
	};

	public String save() {
		logger.info("SignupAction.save(): " + 
			getFirstName() + ' ' + getLastName() +
			'(' + getEmail() + ')' + ' ' + getUserName());
		Person p = personHome.getInstance();
		boolean fail = false;		// User Friendly: Let's check everything at once
		if (isUserNameInUse()) {
			JsfUtil.addMessage("userName", "username in use: " + getUserName());
			fail = true;
		}
		if (isEmailInUse()) {
			JsfUtil.addMessage("email", "email in use: " + getEmail());
			fail = true;
		}
		for (String[] row : BLOCKED_EMAILS) {
			if (row[0].equals(getEmail())) {
				JsfUtil.addMessage("email", row[1]); // contains full snarky message
				fail = true;
				break;
			}
		}
		if (!p.isAcceptedLicense()) {
			JsfUtil.addMessage("acceptedLicense",
				"You must accept the license terms in order to sign up.");
			fail = true;
		}
		if (!p.isMinimumAge()) {
			JsfUtil.addMessage("minimumAge",
				"You must be of or older than the minimum age in order to sign up.");
			fail = true;
		}
		if (fail) {
			return null;
		}
		p.setFirstname(getFirstName());
		p.setLastname(getLastName());
		p.setEmail(getEmail());
		p.setUserName(getUserName());
		p.setPassword(PassPhrase.getNext(8));
		p.setJoined(LocalDate.now());
		p.setPassword(DigestUtils.md5(p.getPassword()));		
		entityManager.persist(p);
		// If that didn't throw an exception, we're good
		// XXX renderer.render("/notifications/welcome-email.xhtml");
		// XXX renderer.render("/notifications/signup.xhtml");
		// XXX Events.instance().raiseEvent(EventNames.EVENT_PERSON_SIGNED_UP);
		logger.info("SignupAction.save(): SAVED");
		return "/";
	}

	/** ActionListener for the UserName field */
	public void verifyUsernameIsAvailable() {
		final String requestedUserName = getUserName().trim();
		logger.info("SignupAction.verifyUsernameIsAvailable: userName=" + requestedUserName);
		if (requestedUserName == null || requestedUserName.length() == 0)
			return;	// Ignore; JSF validator will handle
		if (isUserNameInUse()) {
			JsfUtil.addMessage("userName",
				"Username is in use. Choose another, or use Password Recovery");
			System.out.println("SignupAction.verifyUsernameIsAvailable(): IN USE");
		}
	}

	/** ActionListener for the email field */
	public void verifyEmailIsAvailable() {
		final String requestedEmail = getEmail().trim();
		System.out.println("SignupAction.verifyEmailIsAvailable: email=" + requestedEmail);
		if (requestedEmail == null || requestedEmail.length() == 0)
			return;	// Ignore; JSF validator will handle
		if (isEmailInUse()) {
			JsfUtil.addMessage("email",
				"Username is in use. Choose another, or use Password Recovery");
			System.out.println("SignupAction.verifyEmailIsAvailable(): IN USE");
			return;
		}
		for (String[] row : BLOCKED_EMAILS) {
			if (row[0].equals(requestedEmail)) {
				JsfUtil.addMessage("email", row[1]);
				return;
			}
		}
	}

	// DO NOT INLINE - multiple uses
	public boolean isUserNameInUse() {
		List<Person> list = entityManager.createQuery(
			"select p from Person p where p.userName = ?1", Person.class)
			.setParameter(1, getUserName())
			// Don't use getSingleResult to avoid need for try/catch
			.getResultList();
		System.out.println("SignupAction.isUserNameInUse()\n" + list);
		return list.size() > 0;
	}

	// DO NOT INLINE - multiple uses
	public boolean isEmailInUse() {
		List<Person> list = entityManager.createQuery(
			"select p from Person p where p.email = ?1", Person.class)
			.setParameter(1, getEmail())
			// Don't use getSingleResult to avoid need for try/catch
			.getResultList();
		System.out.println("SignupAction.isEmailInUse()\n" + list);
		return list.size() > 0;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public PersonHome getPersonHome() {
		return personHome;
	}

	public void setPersonHome(PersonHome personHome) {
		this.personHome = personHome;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
