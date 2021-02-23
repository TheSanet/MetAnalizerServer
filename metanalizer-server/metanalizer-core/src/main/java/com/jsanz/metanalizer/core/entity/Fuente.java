package com.jsanz.metanalizer.core.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the fuente database table.
 * 
 */
@Entity
@NamedQuery(name="Fuente.findAll", query="SELECT f FROM Fuente f")
public class Fuente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;

	private String nombre;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_ALTA")
	private Date fechaAlta;



	public Fuente() {
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Fuente [id=" + id + ", nombre=" + nombre + "]";
	}

}