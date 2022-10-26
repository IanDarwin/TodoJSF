package servlet;

import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.darwinsys.util.ResourceProperties;

import auth.LoginConstants;

/**
 * Perform various initializations for the webapp
 * @author Ian Darwin
 */
@WebListener
public class ContextListener implements ServletContextListener {

	private static final String CONFIG_PROPERTIES_NAME = "/config.properties";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Create the Users_List List for the webapp
		ServletContext servletContext = sce.getServletContext(); 
		servletContext.setAttribute(LoginConstants.USERS_LIST, new ArrayList<>());

		// Load the config.properties file
		final Properties properties = ResourceProperties.propertiesFromClassPath(getClass(), CONFIG_PROPERTIES_NAME);

		// Set the signup pin as an init param 
		servletContext.setInitParameter("pin.signup", properties.getProperty("pin.signup"));

		// Set the properties in the servlet context
		servletContext.setAttribute(CONFIG_PROPERTIES_NAME, properties);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Nothing to do
	}
}

