package com.jsanz.metanalizer.ws.endpoint;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


/**
 * Endpoint del servicio web version
 *
 * @author jsanz
 *
 */
@Endpoint
public class VersionEndPoint {

	private static final String GET_TARGET_NAMESPACE = "http://metanalizer.jsanz.com";

	private final Logger logger = LoggerFactory.getLogger(VersionEndPoint.class);
	
	@Value("${version}")
	private String version;

	@PayloadRoot(localPart = "VersionRequest", namespace = GET_TARGET_NAMESPACE)
	public @ResponsePayload JAXBElement<String> versionDetails(@RequestPayload JAXBElement<String> codigoPeticion) {
		return new JAXBElement<>(new QName(String.class.getSimpleName()), String.class, version);
	}

}
