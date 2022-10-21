package action;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import model.Person;

@Named
@SessionScoped
public class PersonHome implements Serializable {

	private static final long serialVersionUID = -2284578724132631798L;

	private Long id;
	private Person instance;
	
	@PersistenceContext EntityManager em;
	
	public PersonHome() {
		System.out.println("PersonHome.PersonHome()");
		instance = new Person();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void wire() {
		System.out.println("Wire(): " + id);
		if (id == null) {
			throw new IllegalStateException("PersonHome.Wire: No ID");
		}
		instance = em.find(Person.class, id);
		if (instance == null) {
			throw new EntityNotFoundException("PersonHome.wire: Person not found by id! " + id);
		}
	}

	public void wire(Long id) {
		System.out.println("PersonHome.wire(" + id + ")");
		setId(id);
		wire();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Transactional(value=TxType.REQUIRED)
	public void update() {
		System.out.println("PersonHome.update()");
		em.merge(instance);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Transactional(value=TxType.REQUIRED)
	public void save() {
		System.out.println("PersonHome.save(): " + instance);
		em.persist(instance);
	}
	
	public void newInstance() {
		System.out.println("PersonHome.newInstance()");
		instance = new Person();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		System.out.println("PersonHome.setId()");
		this.id = id;
	}

	public Person getInstance() {
		// System.out.println("PersonHome.getInstance(): " + instance);
		return instance;
	}
	public void setInstance(Person instance) {
		if (instance == null) {
			throw new NullPointerException("personHome.setInstance");
		}
		System.out.println("PersonHome.setInstance(" + instance + ")");
		this.instance = instance;
	}
}
