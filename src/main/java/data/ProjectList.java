package data;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.inject.Named;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import com.darwinsys.todo.model.Project;

/**
 * This is a basic DAO-like interface for use by JSF or EJB.
 * Methods are implemented for us by Apache DeltaSpike Data.
 * The methods in the inherited interface suffice for many apps!
 * @author Ian Darwin
 */
@Named("projectList") @Default
@SessionScoped
@Repository 
public interface ProjectList extends Serializable, EntityRepository<Project, Long> {

	@Query(value="select p from Project p order by lower(p.name) asc")
	List<Project> findAll();
}
