package com.jsanz.metanalizer.core.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the metajuego database table.
 * 
 */
@Entity
public class Metajuego implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;

	private String nombre;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_DESDE")
	private Date fechaDesde;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_HASTA")
	private Date fechaHasta;
	
	@ManyToOne
    @JoinColumn(name = "ID_AMBITO")
	private Ambito ambito;

	@ManyToOne
    @JoinColumn(name="ID_FORMATO")
	private Formato formato;

	@ManyToOne
    @JoinColumn(name="ID_FUENTE")
	private Fuente fuente;
	
	private Boolean consolidado;

	public Metajuego() {
	}

	public Boolean getConsolidado() {
		return consolidado;
	}

	public void setConsolidado(Boolean consolidado) {
		this.consolidado = consolidado;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = java.sql.Date.valueOf(fechaAlta);
	}

	public LocalDate getFechaDesde() {
		return fechaDesde.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public void setFechaDesde(LocalDate fechaDesde) {
		this.fechaDesde = java.sql.Date.valueOf(fechaDesde);
	}

	public LocalDate getFechaHasta() {
		return fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public void setFechaHasta(LocalDate fechaHasta) {
		this.fechaHasta = java.sql.Date.valueOf(fechaHasta);
	}



	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Ambito getAmbito() {
		return ambito;
	}

	public void setAmbito(Ambito ambito) {
		this.ambito = ambito;
	}

	public Formato getFormato() {
		return formato;
	}

	public void setFormato(Formato formato) {
		this.formato = formato;
	}

	public Fuente getFuente() {
		return fuente;
	}

	public void setFuente(Fuente fuente) {
		this.fuente = fuente;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Metajuego [id=" + id + ", nombre=" + nombre + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Metajuego other = (Metajuego) obj;
		if (id != other.id)
			return false;
		return true;
	}



}