package com.jsanz.metanalizer.ws.endpoint;

import java.time.LocalDate;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.jsanz.metanalizer.ConsultaSimpleRequest;
import com.jsanz.metanalizer.ConsultaSimpleResponse;
import com.jsanz.metanalizer.Format;
import com.jsanz.metanalizer.SimplestArquetipo;
import com.jsanz.metanalizer.SimplestMetajuego;
import com.jsanz.metanalizer.core.entity.Ambito;
import com.jsanz.metanalizer.core.entity.Arquetipo;
import com.jsanz.metanalizer.core.entity.Metajuego;
import com.jsanz.metanalizer.core.service.MetajuegoService;

@Endpoint
public class ConsultaSimpleEndPoint {
	
	private static final String GET_TARGET_NAMESPACE = "http://metanalizer.jsanz.com";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MetajuegoService metajuegoService;

	@PayloadRoot(localPart = "ConsultaSimpleRequest", namespace = GET_TARGET_NAMESPACE)
	public @ResponsePayload ConsultaSimpleResponse consultaSimpleDetails(@RequestPayload ConsultaSimpleRequest request) throws Exception {
		Ambito ambitoMundial=metajuegoService.getAmbitoMundial();
		Metajuego metajuego=metajuegoService.getConsolidadoByAmbitoBetweenFechas(ambitoMundial, request.getFechaInicio(),request.getFechaFin());
		if(metajuego==null) {
			return new ConsultaSimpleResponse();
		}else {
			List<Arquetipo> listaArquetipo=metajuegoService.getArquetipoByMetajuego(metajuego.getId());
			ConsultaSimpleResponse responde=new ConsultaSimpleResponse();

			responde.setFechaInicio(DatatypeFactory.newInstance().newXMLGregorianCalendar(metajuego.getFechaDesde().toString()));
			responde.setFechaFin(DatatypeFactory.newInstance().newXMLGregorianCalendar(metajuego.getFechaHasta().toString()));
			
			Format formato=new Format();
			formato.setNombre(metajuego.getFormato().getNombre());
			responde.setFormato(formato);
	
			SimplestMetajuego sMetajuego= new SimplestMetajuego();
			for(Arquetipo arquetipo: listaArquetipo) {
				SimplestMetajuego.Entry entry=new SimplestMetajuego.Entry();
				SimplestArquetipo simplestArquetipo=new SimplestArquetipo();
				simplestArquetipo.setFormato(formato);
				simplestArquetipo.setNombre(arquetipo.getNombre());
				entry.setKey(simplestArquetipo);
				entry.setValue(Long.valueOf(metajuegoService.getProporcionByIdMetajuegoIdArquetipo(metajuego.getId(),arquetipo.getId()).getTantoPorMil()));
				sMetajuego.getEntry().add(entry);
			}
			responde.setData(sMetajuego);
			return responde;
		}
	}

}
