package jsf;

/** THIS IS A MOCK for the static methods/fields in the real JsfUtil.
 * Must only be on test classpath, or bad stuff will happen!
 * @author Eclipse IDE
 */
public class JsfUtil {

	public static final String FORCE_REDIRECT = "?faces-redirect=true";

	public static void addMessage(String component, String message) {
		System.out.printf("Mock JsfUtil: adding message '%s' to component %s\n", message, component);
	}

	public static void addMessage(String message) {
		System.out.printf("Mock JsfUtil: adding message '%s'\n", message);
	}

}
