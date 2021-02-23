package com.jsanz.metanalizer.batch.data;

import java.util.List;

import com.jsanz.metanalizer.core.entity.Arquetipo;
import com.jsanz.metanalizer.core.entity.Metajuego;
import com.jsanz.metanalizer.core.entity.Proporcion;

public class MetajuegoData {
	
	private Metajuego metajuego;
	
	private List<Arquetipo> listaArquetipo;
	
	private List<Proporcion> listaProporcion;
	
	public Metajuego getMetajuego() {
		return metajuego;
	}

	public void setMetajuego(Metajuego metajuego) {
		this.metajuego = metajuego;
	}

	public List<Arquetipo> getListaArquetipo() {
		return listaArquetipo;
	}

	public void setListaArquetipo(List<Arquetipo> listaArquetipo) {
		this.listaArquetipo = listaArquetipo;
	}

	public List<Proporcion> getListaProporcion() {
		return listaProporcion;
	}

	public void setListaProporcion(List<Proporcion> listaProporcion) {
		this.listaProporcion = listaProporcion;
	}


}
