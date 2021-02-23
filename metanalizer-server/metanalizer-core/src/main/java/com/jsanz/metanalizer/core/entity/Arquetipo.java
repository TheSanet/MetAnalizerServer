package com.jsanz.metanalizer.core.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the arquetipo database table.
 * 
 */
@Entity
@NamedQuery(name="Arquetipo.findAll", query="SELECT a FROM Arquetipo a")
public class Arquetipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

    @Column(name="ID_FORMATO")
	private Integer idFormato;

	private String nombre;

	public Arquetipo() {
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

	public Integer getIdFormato() {
		return idFormato;
	}

	public void setIdFormato(Integer idFormato) {
		this.idFormato = idFormato;
	}
	
	@Override
	public String toString() {
		return "Arquetipo [id=" + id + ", nombre=" + nombre + "]";
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
		Arquetipo other = (Arquetipo) obj;
		if (id != other.id)
			return false;
		return true;
	}

}