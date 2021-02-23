package com.jsanz.metanalizer.batch.scraping.reader;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsanz.metanalizer.batch.service.BaseScraping;
import com.jsanz.metanalizer.core.dicionario.ListaFormato;
import com.jsanz.metanalizer.core.entity.Arquetipo;
import com.jsanz.metanalizer.core.entity.ArquetipoMetajuego;
import com.jsanz.metanalizer.core.entity.Formato;
import com.jsanz.metanalizer.core.entity.Metajuego;
import com.jsanz.metanalizer.core.entity.Proporcion;
import com.jsanz.metanalizer.core.service.MetajuegoService;

@Component
public class MtgGolfishScrapingReader implements ItemReader<Object>, BaseScraping {
	
	Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MetajuegoService metajuegoService;
	
	ArrayList<Object> listaEntidades;
	Iterator<Object> iterador;

	@BeforeStep
	public void before() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		listaEntidades=new ArrayList<Object>();

		String url = "https://www.mtggoldfish.com/metagame/pauper/full#paper";
		Document document=getHtmlDocument(url);
		
		Metajuego metajuego=new Metajuego();
		Integer idMetajuego=metajuegoService.getNextIdMetajuego();

		metajuego.setConsolidado(false);
		metajuego.setFechaAlta(LocalDate.now());
		metajuego.setFechaDesde(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
		metajuego.setFechaHasta(LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()));
		metajuego.setNombre("MtgGolfish");
		metajuego.setFuente(metajuegoService.getFuenteWebScraping());
		metajuego.setAmbito(metajuegoService.getAmbitoMundial());
		
		//Formato
		Elements listaElementosFormato=document.select("div.layout-breadcrumb");
		String sFormato=listaElementosFormato.first().text();
		sFormato=sFormato.replace("Browse > Home / Decks / Metagame / ", "");
		sFormato=sFormato.replace(" / All Decks", "");
		Integer idFormato=ListaFormato.Match(sFormato);
		Formato formato=metajuegoService.getFormato(idFormato);
		metajuego.setFormato(formato);
		
		metajuego.setId(idMetajuego);
		
		listaEntidades.add((Object)metajuego);
		
		//Arquetipos
		Elements listaElemento=document.select("div.archetype-tile-description-wrapper");
		logger.info("Numero de elementos leidos: {}",listaElemento.size());
		int i=1;
		
		Integer idArquetipo=metajuegoService.getNextIdArquetipo();
		Integer idProporcion=metajuegoService.getNextIdProporcion();
		Integer idarquetipoMetajuego=metajuegoService.getNextIdArquetipoMetajuego();
		for(Element elemento: listaElemento) {
			Arquetipo arquetipo=new Arquetipo();
			arquetipo.setIdFormato(idFormato);
			arquetipo.setId(idArquetipo);
			
			
			Proporcion proporcion=new Proporcion();
			proporcion.setIdArquetipo(arquetipo.getId());
			proporcion.setIdMetajuego(metajuego.getId());
			

			Elements listaNombre=elemento.getElementsByClass("deck-price-paper");
			Element eNombre=listaNombre.first();
			String nombre=eNombre.text();
			Elements listaProporcion=elemento.getElementsByClass("archetype-tile-statistic-value");
			Element eProporcion=listaProporcion.first();
			String proporcionPlusNumber=eProporcion.text();
			String sproporcion=proporcionPlusNumber.split("%", 2)[0];
			String sNumero=proporcionPlusNumber.split("%", 2)[1];
			String numero=sNumero.replace("(", "").replace(")", "").trim();
			
			arquetipo.setNombre(nombre);
			
			ArquetipoMetajuego arquetipoMetajuego=new ArquetipoMetajuego();
			arquetipoMetajuego.setIdArquetipo(idArquetipo);
			arquetipoMetajuego.setIdMetajuego(idMetajuego);
			arquetipoMetajuego.setId(idarquetipoMetajuego);
			listaEntidades.add((Object)arquetipoMetajuego);
			idarquetipoMetajuego++;

			listaEntidades.add((Object)arquetipo);
			Float floatProporcion=Float.valueOf(sproporcion)*10;
			proporcion.setTantoPorMil(Math.round(floatProporcion));
			proporcion.setIdArquetipo(idArquetipo);
			proporcion.setId(idProporcion);
			proporcion.setNumero(Integer.parseInt(numero));
			listaEntidades.add((Object)proporcion);
			
			logger.debug("Elemento {}-eismo: {} con proporcion", i, arquetipo, proporcion);
			i++;
			idArquetipo++;
			idProporcion++;
		}
		
		iterador=listaEntidades.iterator();
	}

	@Override
	public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(iterador.hasNext()) {
			Object o=iterador.next();
			logger.info("Lee {}",o);
			return o;
		}else {
			return null;
		}
	}

}
