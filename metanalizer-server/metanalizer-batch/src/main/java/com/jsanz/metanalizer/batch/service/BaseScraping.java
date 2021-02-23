package com.jsanz.metanalizer.batch.service;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.batch.item.ItemReader;

public interface BaseScraping{
	
	/**
	 * Con este método devuelvo un objeto de la clase Document con el contenido del
	 * HTML de la web que me permitirá parsearlo con los métodos de la librelia JSoup
	 * @param url
	 * @return Documento con el HTML
	 * @throws IOException 
	 */
	public default Document getHtmlDocument(String url) throws IOException {
	    Document doc = null;
	    //antes
//	    doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
	    //ahora
		doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36").timeout(1000000).get();
	    return doc;
	}

}
