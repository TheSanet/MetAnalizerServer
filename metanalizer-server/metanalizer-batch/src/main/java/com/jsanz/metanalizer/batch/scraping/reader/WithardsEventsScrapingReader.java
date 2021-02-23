package com.jsanz.metanalizer.batch.scraping.reader;

import java.security.Provider.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

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
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.stereotype.Component;

import com.jsanz.metanalizer.batch.service.BaseScraping;
import com.jsanz.metanalizer.core.dicionario.ListaFormato;
import com.jsanz.metanalizer.core.entity.Metajuego;
import com.jsanz.metanalizer.core.service.MetajuegoService;

@Component
public class WithardsEventsScrapingReader implements BaseScraping, ItemReader<Object>{
	
	Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MetajuegoService metajuegoService;
	
	ArrayList<String> listaUrl;
	
	ArrayList<Object> listaEntidades;
	Iterator<Object> iterador;
	
	@BeforeStep
	public void before() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		listaUrl=new ArrayList<String>();
		listaEntidades=new ArrayList<Object>();

		String url = "https://magic.wizards.com/en/content/deck-lists-magic-online-products-game-info";
		Document document=getHtmlDocument(url);
		
		Elements listaUrlElement=document.select("div.article-item-extended");
		for(Element urlElement:listaUrlElement) {
			String url2=urlElement.select("[href]").attr("abs:href");
			listaUrl.add(url2);
		}
		
		Integer idmetajuego=metajuegoService.getNextIdMetajuego();
		for(String url3:listaUrl) {
			Metajuego metajuego =new Metajuego();
			Document document2=getHtmlDocument(url3);
			
			//ambito formato y nombre
			String nombre=document2.select("h1").text();
			metajuego.setNombre(nombre.concat(" MOL"));
			metajuego.setFormato(metajuegoService.getFormato(ListaFormato.Contains(nombre)));
			metajuego.setAmbito(metajuegoService.getAmbitoMundial());
			metajuego.setConsolidado(false);
			
			//fechas
			metajuego.setFechaAlta(LocalDate.now());
			Elements elementsFecha=document2.select("p.posted-in");
			String fecha=elementsFecha.text();
			fecha=fecha.split("on")[1].trim();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", new Locale("en"));
			LocalDate date=LocalDate.parse(fecha,formatter);
			metajuego.setFechaDesde(date);
			metajuego.setFechaHasta(date);
			metajuego.setFuente(metajuegoService.getFuenteWebScraping());
			
			//arquetipos y proporciones
			//TODO heramienta para discernir que arquetipo es
			
			metajuego.setId(idmetajuego);
			idmetajuego++;
		}
	}

	@Override
	public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		return null;
	}

}
