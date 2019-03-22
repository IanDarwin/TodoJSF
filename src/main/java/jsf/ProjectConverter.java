package jsf;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.darwinsys.todo.model.Project;

@ManagedBean
@RequestScoped
public class ProjectConverter implements Converter{

	@PersistenceContext
	private transient EntityManager em;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		if (submittedValue == null || submittedValue.isEmpty()) {
			return null;
		}

		try {
			return em.find(Project.class, Long.valueOf(submittedValue));
		} catch (NumberFormatException e) {
			throw new ConverterException(new FacesMessage(String.format("%s is not a valid Project ID", submittedValue)), e);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		if (modelValue == null || modelValue.toString().equals("")) {
			return "";
		}

		if (modelValue instanceof Project) {
			return String.valueOf(((Project) modelValue).getId());
		} else {
			throw new ConverterException(new FacesMessage(String.format("%s is not a valid Project", modelValue)));
		}
	}
}
