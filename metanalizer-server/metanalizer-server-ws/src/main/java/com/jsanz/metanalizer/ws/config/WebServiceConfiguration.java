package com.jsanz.metanalizer.ws.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.validation.XmlValidator;
import org.springframework.xml.validation.XmlValidatorFactory;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.XsdSchemaCollection;


/**
 * Clase de configuracion del servicio web<br/>
 * En esta clase se configura el WSDL, interceptores, XSDs, etc.
 *
 * @author MRDEPEFR
 *
 */
@Configuration
@EnableWs
@PropertySource(value = { "classpath:ws.properties" })
@SuppressWarnings("static-method")
@ComponentScan({ "com.jsanz.metanalizer.ws" })
public class WebServiceConfiguration extends WsConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(WebServiceConfiguration.class);

	/*
	 * Inyecta la variable utilizada para acceder al fichero de configuracion
	 * definido con la anotacion \@PropertySource.
	 */
	@Autowired
	private Environment environment;
	
	@Bean(name = "version")
	public DefaultWsdl11Definition version() {
		final DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setTargetNamespace("http://metanalizer.jsanz.com/version");
		definition.setPortTypeName("VersionWS");
		definition.setLocationUri("/endpoints/");
		definition.setSchema(this.versionXsd());
		return definition;
	}


	// Es necesario publicar todos los XSD para que se pueda generar el WSDL
	// dinamicamente
	// Si no se definen a la hora de acceder al WSDL se lanzaran excepciones
	// indicando que no se localizan estos XSD
	
	@Bean
	public XsdSchema versionXsd() {
	    return new SimpleXsdSchema(new ClassPathResource("schemas/Version.xsd"));
	}
	
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
//		interceptors.add(new LoggerInterceptor());
		interceptors.add(this.getValidatingInterceptor());
	}

	/**
	 * Interceptor encargado de validar las peticiones contra el XSD
	 *
	 * @return
	 */
	private PayloadValidatingInterceptor getValidatingInterceptor() {
		final PayloadValidatingInterceptor validatingInterceptor = new PayloadValidatingInterceptor();
		validatingInterceptor.setValidateRequest(true);
		validatingInterceptor.setValidateResponse(false);
		validatingInterceptor.setXsdSchema(this.versionXsd());
		return validatingInterceptor;
	}

}