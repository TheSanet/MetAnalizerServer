package com.jsanz.metanalizer.core.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ambito database table.
 * 
 */
@Entity
public class Ambito implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	
	private String descripcion;

	public Ambito() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Ambito [id=" + id + ", descripcion=" + descripcion + "]";
	}



}