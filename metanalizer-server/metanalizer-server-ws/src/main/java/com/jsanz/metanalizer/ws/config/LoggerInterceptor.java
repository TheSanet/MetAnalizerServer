package com.jsanz.metanalizer.ws.config;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.xml.transform.TransformerObjectSupport;

/**
 * Interceptor utilizado para capturar los mensajes de entrada y salida del
 * servicio web e imprimirlos por consola
 *
 * @author MRDEPEFR
 *
 */
public class LoggerInterceptor extends TransformerObjectSupport implements EndpointInterceptor {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
		this.logMessageSource("Request SOAP\n", messageContext.getRequest().getPayloadSource());
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
		this.logMessageSource("Response SOAP\n", messageContext.getResponse().getPayloadSource());
		return true;
	}

	@Override
	public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
		this.logMessageSource("Fault SOAP\n", messageContext.getResponse().getPayloadSource());
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {
		// No nos hace falta
	}

	protected void logMessageSource(String logMessage, Source source) throws TransformerException {
		if (source != null) {
			final Transformer transformer = this.createNonIndentingTransformer();
			final StringWriter writer = new StringWriter();
			transformer.transform(source, new StreamResult(writer));
			this.logger.debug(StringUtils.join(logMessage, writer.toString()));
		}
	}

	private Transformer createNonIndentingTransformer() throws TransformerConfigurationException {
		final Transformer transformer = this.createTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "no");
		return transformer;
	}
}

