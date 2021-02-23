package com.jsanz.metanalizer.ws;

import java.util.List;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.factory.annotation.Autowired;

import com.jsanz.metanalizer.core.entity.Ambito;
import com.jsanz.metanalizer.core.entity.Arquetipo;
import com.jsanz.metanalizer.core.entity.Metajuego;
import com.jsanz.metanalizer.core.service.MetajuegoService;
import com.jsanz.metanalizer.ws.entity.ConsultaSimpleRequest;
import com.jsanz.metanalizer.ws.entity.ConsultaSimpleResponse;
import com.jsanz.metanalizer.ws.entity.Format;
import com.jsanz.metanalizer.ws.entity.SimplestArquetipo;
import com.jsanz.metanalizer.ws.entity.SimplestMetajuego;
import com.jsanz.metanalizer.ws.entity.SimplestMetajuego.Entry;


@WebService(endpointInterface = "com.jsanz.metanalizer.ws.ConsultaSimple")
public class ConsultaSimple implements IConsultaSimple{
	
	@Autowired
	private MetajuegoService metajuegoService;

	@Override
	public ConsultaSimpleResponse consultaSimple(ConsultaSimpleRequest request) throws DatatypeConfigurationException {
		Ambito ambitoMundial=metajuegoService.getAmbitoMundial();
		Metajuego metajuego=metajuegoService.getConsolidadoByAmbitoBetweenFechas(ambitoMundial, request.getFechaInicio(),request.getFechaFin());
		List<Arquetipo> listaArquetipo=metajuegoService.getArquetipoByMetajuego(metajuego.getId());
		ConsultaSimpleResponse responde=new ConsultaSimpleResponse();
		responde.setFechaInicio(DatatypeFactory.newInstance().newXMLGregorianCalendar(metajuego.getFechaDesde().toString()));
		responde.setFechaFin(DatatypeFactory.newInstance().newXMLGregorianCalendar(metajuego.getFechaHasta().toString()));
		Format formato=new Format();
		formato.setNombre(metajuego.getFormato().getNombre());
		responde.setFormato(formato);

		SimplestMetajuego sMetajuego= new SimplestMetajuego();
		SimplestMetajuego.Entry entry=new SimplestMetajuego.Entry();
		for(Arquetipo arquetipo: listaArquetipo) {
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
