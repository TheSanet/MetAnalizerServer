package com.jsanz.metanalizer.ws.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;


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
	
	@Bean(name = "metanalizer")
	public DefaultWsdl11Definition metanalizer() throws Exception {
		final DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setTargetNamespace("http://metanalizer.jsanz.com/metanalizer");
		definition.setPortTypeName("metanalizer");
		definition.setLocationUri("/");
		definition.setSchemaCollection(this.schemaCollections());
		return definition;
	}
	
	@Bean
	public XsdSchemaCollection schemaCollections() throws Exception {
	    CommonsXsdSchemaCollection xsds = new CommonsXsdSchemaCollection(new ClassPathResource("schemas/Version.xsd"), new ClassPathResource("schemas/ConsultaSimple.xsd"));
	    xsds.setInline(true); 
	    return xsds;
	}


	// Es necesario publicar todos los XSD para que se pueda generar el WSDL
	// dinamicamente
	// Si no se definen a la hora de acceder al WSDL se lanzaran excepciones
	// indicando que no se localizan estos XSD
	
//	@Bean
//	public XsdSchema ConsultaSimpleXsd() {
//	    return new SimpleXsdSchema(new ClassPathResource("schemas/operation/ConsultaSimple.xsd"));
//	}
//	
//	@Bean
//	public XsdSchema consultaSimpleRequestTypeXsd() {
//		return new SimpleXsdSchema(new ClassPathResource("schemas/types/ConsultaSimpleRequestType.xsd"));
//	}
//	
//	@Bean
//	public XsdSchema consultaSimpleResponseTypeXsd() {
//		return new SimpleXsdSchema(new ClassPathResource("schemas/types/ConsultaSimpleResponseType.xsd"));
//	}
	
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(new LoggerInterceptor());
		try {
//			interceptors.add(this.getValidatingInterceptor());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Interceptor encargado de validar las peticiones contra el XSD
	 *
	 * @return
	 * @throws Exception 
	 */
	private PayloadValidatingInterceptor getValidatingInterceptor() throws Exception {
		final PayloadValidatingInterceptor validatingInterceptor = new PayloadValidatingInterceptor();
		validatingInterceptor.setValidateRequest(true);
		validatingInterceptor.setValidateResponse(false);
		validatingInterceptor.setXsdSchemaCollection(schemaCollections());
		return validatingInterceptor;
	}

}