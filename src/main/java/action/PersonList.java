package action;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Named;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import model.Person;

@Named("personList")  @Default
@ApplicationScoped
@Repository 
public interface PersonList extends EntityRepository<Person, Long> {

	// Most methods are inherited.
	
	/** Find a person given their email address */
	Person findByEmail(String email);
}
