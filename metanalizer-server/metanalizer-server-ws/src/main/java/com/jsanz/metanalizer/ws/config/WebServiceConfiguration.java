package com.jsanz.metanalizer.ws.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
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
public class WebServiceConfiguration extends WsConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(WebServiceConfiguration.class);

	/*
	 * Inyecta la variable utilizada para acceder al fichero de configuracion
	 * definido con la anotacion \@PropertySource.
	 */
	@Autowired
	private Environment environment;
	
	@Bean(name = "VersionWS")
	public DefaultWsdl11Definition dynamicWsdl() {
		final DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setTargetNamespace("http://metanalizer.jsanz.com/version");
		definition.setPortTypeName("VersionWS");
		definition.setLocationUri("/endpoints/");
		definition.setSchema(this.version());
		return definition;
	}


	// Es necesario publicar todos los XSD para que se pueda generar el WSDL
	// dinamicamente
	// Si no se definen a la hora de acceder al WSDL se lanzaran excepciones
	// indicando que no se localizan estos XSD

	@Bean(name = "versionRequest")
	public SimpleXsdSchema versionRequest() {
		return new SimpleXsdSchema(new ClassPathResource("schemas/VersionResponse.xsd"));
	}

	@Bean(name = "versionResponse")
	public SimpleXsdSchema versionResponse() {
		return new SimpleXsdSchema(new ClassPathResource("schemas/VersionRequest.xsd"));
	}
	
	@Bean
	public XsdSchema version() {
	    return new SimpleXsdSchema(new ClassPathResource("schemas/Version.xsd"));
	}

}