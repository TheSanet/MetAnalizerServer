package com.jsanz.metanalizer.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;

import com.jsanz.metanalizer.ws.entity.ConsultaSimpleRequest;
import com.jsanz.metanalizer.ws.entity.ConsultaSimpleResponse;


@WebService
public interface IConsultaSimple {
	
	@WebMethod
	public ConsultaSimpleResponse consultaSimple(ConsultaSimpleRequest request) throws DatatypeConfigurationException;

}
