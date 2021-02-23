package com.jsanz.metanalizer.ws;

import javax.xml.ws.Endpoint;

public class Publish {

	public static void main(String[] args) {
		 Endpoint.publish("http://localhost:8081/consulta-simple", new ConsultaSimple());
	}

}
