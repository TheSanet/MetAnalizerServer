package com.jsanz.metanalizer.ws.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.hibernate.cfg.Environment;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.ws.transport.http.support.AbstractAnnotationConfigMessageDispatcherServletInitializer;

/**
 * ConfiguraciÃ³n DispatcherServlet.
 * 
 * @author @xantygc
 */
public class SpringWebAppInitializer extends AbstractAnnotationConfigMessageDispatcherServletInitializer {

	private ServletContext servletContext;
	
	
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/*" };
	}

	@Override
	public boolean isTransformWsdlLocations() {
		return true;
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {  WebServiceConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(getMultiPartConfig());
		super.customizeRegistration(registration);
	}

	private MultipartConfigElement getMultiPartConfig() {
		String location = "";
		long maxFileSize = 5L * 1024L * 1024L;
		long maxRequestSize = 10L * 1024L * 1024L;
		int fileSizeThreshold = 0;
		return new MultipartConfigElement(location, maxFileSize, maxRequestSize, fileSizeThreshold);
	}
	
	@Override
	public void onStartup(ServletContext servletContext_) throws ServletException {
		this.servletContext = servletContext_;
		super.onStartup(servletContext_);
	}
	
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		final WebApplicationContext webApplicationContext = super.createRootApplicationContext();
		try {
			ResourcePropertySource source=new ResourcePropertySource("classpath:ws.properties");
			ConfigurableEnvironment env=((ConfigurableEnvironment) webApplicationContext.getEnvironment());
			env.getPropertySources().addFirst(source);
			final String springDefaultProfile = webApplicationContext.getEnvironment().getRequiredProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
			this.servletContext.setInitParameter(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, springDefaultProfile);
			System.out.println(String.format("Se ha inicializado la aplicación con el perfil de Spring %s", springDefaultProfile));
		} catch (final IOException e) {
			System.out.println("No se ha podido obtener el perfil de Spring predeterminado del fichero de propiedades ws.properties");
		}
		return webApplicationContext;
	}

}