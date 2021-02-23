package com.jsanz.metanalizer.core.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the proporcion database table.
 * 
 */
@Entity
public class Proporcion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	
	@Column(name="ID_ARQUETIPO")
	private Integer idArquetipo;
	
	@Column(name="ID_METAJUEGO")
	private Integer idMetajuego;

	@Column(name= "TANTO_POR_MIL")
	private Integer tantoPorMil;
	
	@Column(name="NUMERO")
	private Integer numero;

	public Proporcion() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTantoPorMil() {
		return this.tantoPorMil;
	}

	public void setTantoPorMil(Integer tantoPorMil) {
		this.tantoPorMil = tantoPorMil;
	}
	
	public Integer getIdArquetipo() {
		return idArquetipo;
	}

	public void setIdArquetipo(Integer idArquetipo) {
		this.idArquetipo = idArquetipo;
	}

	public Integer getIdMetajuego() {
		return idMetajuego;
	}

	public void setIdMetajuego(Integer idMetajuego) {
		this.idMetajuego = idMetajuego;
	}
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Proporcion [id=" + id + ", idArquetipo=" + idArquetipo + ", idMetajuego=" + idMetajuego
				+ ", tantoPorMil=" + tantoPorMil + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Proporcion other = (Proporcion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}