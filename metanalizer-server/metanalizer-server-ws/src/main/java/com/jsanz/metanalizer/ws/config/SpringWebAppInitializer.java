package com.jsanz.metanalizer.ws.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * ConfiguraciÃ³n DispatcherServlet.
 * 
 * @author @xantygc
 */
public class SpringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
//		return new Class[] { DatabaseConfig.class };
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
//		return new Class<?>[] { WebMvcConfig.class };
		return null;
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return new Filter[] { };
//		return new Filter[] { characterEncodingFilter};

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

}