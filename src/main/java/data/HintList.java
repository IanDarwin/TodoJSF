package data;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.inject.Named;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import com.darwinsys.todo.model.Hint;

/**
 * This is a basic DAO-like interface for use by JSF or EJB.
 * Methods are implemented for us by Apache DeltaSpike Data.
 * The methods in the inherited interface suffice for many apps!
 * @author Ian Darwin
 */
@Named("hintList") @Default
@SessionScoped
@Repository 
public abstract class HintList extends AbstractEntityRepository<Hint, Long> {


	static Random r = new Random();

	/**
	 * Meant to be useful on Todo pages: print one randomly-chosen Hint.
	 */
	public Hint getRandom() {
		List<Hint> hints = findAll();
		long count = hints.size();
		if (count == 0) {
			System.err.println("No hints in database");
			Hint h = new Hint();
			h.setHint("A stitch in time saves nine");
			return h;
		} else {
			System.out.printf("There are %d Hint(s)%n", count);
		}
		int index = r.nextInt((int)count); // JPA starts at 1

		System.out.printf("Index = %d%n", index);

		return hints.get(index);
	}
}
