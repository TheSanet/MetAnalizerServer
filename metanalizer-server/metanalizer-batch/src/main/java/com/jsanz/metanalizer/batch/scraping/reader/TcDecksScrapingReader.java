package com.jsanz.metanalizer.batch.scraping.reader;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsanz.metanalizer.batch.service.BaseScraping;
import com.jsanz.metanalizer.core.dicionario.ListaFormato;
import com.jsanz.metanalizer.core.entity.Ambito;
import com.jsanz.metanalizer.core.entity.Arquetipo;
import com.jsanz.metanalizer.core.entity.Metajuego;
import com.jsanz.metanalizer.core.entity.Proporcion;
import com.jsanz.metanalizer.core.service.MetajuegoService;

@Component
public class TcDecksScrapingReader implements BaseScraping, ItemReader<Object> {
	
	Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MetajuegoService metajuegoService;
	
	ArrayList<String> listaUrl;
	
	ArrayList<Object> listaEntidades;
	Iterator<Object> iterador;

	private boolean cargaInicial=true;

	@BeforeStep
	public void before() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		listaUrl=new ArrayList<String>();
		listaEntidades=new ArrayList<Object>();

		String url = "https://www.tcdecks.net/format.php?format=Pauper";
		Document document=getHtmlDocument(url);
		
		Elements listaElementos=document.getElementsByClass("tourney_list");
		listaElementos=listaElementos.get(0).select("tr");
		for(Element element:listaElementos) {
			if(!element.select(".principal").isEmpty()) {
				Elements elementUrl=element.select(".principal");
				String nombreTorneo=elementUrl.select("[href]").text();
				String url2=elementUrl.select("[href]").attr("abs:href");
				String fecha=element.select("[data-th]").select(":matchesOwn(^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$)").text();
				
				if (cargaInicial || (new SimpleDateFormat("dd/MM/yyyy").parse(fecha).before(Date.valueOf(LocalDate.now().minusMonths(1))) && 
						new SimpleDateFormat("dd/MM/yyyy").parse(fecha).after(Date.valueOf(LocalDate.now().minusMonths(1))))) {
					listaUrl.add(url2);
				}
			}
		}
		
		Integer idMetajuego=metajuegoService.getNextMetajuegoId();
		Integer idAmbito=metajuegoService.getNextIdAmbito();
		Integer idArquetipo=metajuegoService.getNextIdArquetipo();
		Integer idProporcion=metajuegoService.getNextIdProporcion();
		
		for(String url2:listaUrl) {
			Metajuego metajuego =new Metajuego();
			
			//fecha
			Document document2=getHtmlDocument(url2);
			Elements eTitulo=document2.select("h5").select(":not(:has([href]))").select("h5");
			String titulo=eTitulo.text();
			String[] aTitulo=titulo.split("\\|");
			String sFormato=aTitulo[0].replaceAll("Format:", "").trim();
			String fecha=aTitulo[2].replaceAll("Date:", "").replace("No similar events found", "").trim();
			metajuego.setFechaAlta(LocalDate.now());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			metajuego.setFechaDesde(LocalDate.parse(fecha, formatter));
			metajuego.setFechaHasta(LocalDate.parse(fecha, formatter));
			metajuego.setFuente(metajuegoService.getFuenteWebScraping());
			metajuego.setFormato(metajuegoService.getFormato(ListaFormato.Match(sFormato)));
			metajuego.setConsolidado(false);
			
			//ambito y nombre
			Elements listaElementosTituloTroneo=document2.select("h3");
			String tituloTorneo=listaElementosTituloTroneo.first().text();
			String[] arrayNombreTorneo=tituloTorneo.split("\\(");
			if(Arrays.asList(arrayNombreTorneo).size()>1 && arrayNombreTorneo[1]!=null) {
				Ambito ambito=metajuegoService.getAmbitoByNombre(arrayNombreTorneo[1]);
				if(ambito!=null) {
					metajuego.setAmbito(ambito);
				}else {
					ambito=new Ambito();
					ambito.setDescripcion(arrayNombreTorneo[1].replace(")", "").trim());
					ambito.setId(metajuegoService.getNextIdAmbito());
					metajuego.setAmbito(ambito);
				}
				listaEntidades.add((Object)ambito);
			}else {
				Ambito ambito=metajuegoService.getAmbitoMundial();
				metajuego.setAmbito(ambito);
			}
			metajuego.setNombre(arrayNombreTorneo[0]);
			metajuego.setId(idMetajuego);

			
			//Arquetipos
			HashMap<String, Integer> map=new HashMap<String, Integer>();
			Elements elementos=document2.select("[data-th=\"Archetype\"]");
			for(Element nodo: elementos) {
				if(map.containsKey(nodo.text())) {
					map.put(nodo.text(),map.get(nodo.text())+1);
				}else {
					map.put(nodo.text(), 1);
				}
			}
			
			for(String key :map.keySet()) {
				//arq
				Arquetipo arquetipo=new Arquetipo();
				arquetipo.setNombre(key);
				arquetipo.setIdFormato(ListaFormato.Match(sFormato));
				arquetipo.setId(idArquetipo);
				
				//pro
				Proporcion proporcion= new Proporcion();
				proporcion.setIdArquetipo(idArquetipo);
				proporcion.setIdMetajuego(idMetajuego);
				proporcion.setNumero(map.get(key));
				proporcion.setId(idProporcion);
				proporcion.setTantoPorMil(Math.round(map.get(key)/elementos.size()*1000));
				
				idProporcion++;
				idArquetipo++;
				listaEntidades.add((Object)arquetipo);
				listaEntidades.add((Object)proporcion);
				
			}
			idMetajuego++;
			listaEntidades.add((Object)metajuego);
		}
		
		iterador=listaEntidades.iterator();
	}

	@Override
	public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(iterador.hasNext()) {
			Object o=iterador.next();
			logger.info("Lee url: {}",o);
			return o;
		}else {
			return null;
		}
	}

}
