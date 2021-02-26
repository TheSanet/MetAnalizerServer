package com.jsanz.metanalizer.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;

import com.jsanz.metanalizer.ws.entity.VersionRequest;
import com.jsanz.metanalizer.ws.entity.VersionResponse;


@WebService
public interface IVersion {
	
	@WebMethod
	public VersionResponse consultaVersion(VersionRequest request) throws DatatypeConfigurationException;

}
