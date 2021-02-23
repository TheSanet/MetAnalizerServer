package com.jsanz.metanalizer.core.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the formato database table.
 * 
 */
@Entity
@NamedQuery(name="Formato.findAll", query="SELECT f FROM Formato f")
public class Formato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String nombre;

	public Formato() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Formato [id=" + id + ", nombre=" + nombre + "]";
	}

}