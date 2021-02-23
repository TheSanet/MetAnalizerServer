package com.jsanz.metanalizer.core.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the arquetipo_metajuego database table.
 * 
 */
@Entity
@Table(name="arquetipo_metajuego")
@NamedQuery(name="ArquetipoMetajuego.findAll", query="SELECT a FROM ArquetipoMetajuego a")
public class ArquetipoMetajuego implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="ID_ARQUETIPO")
	private int idArquetipo;

	@Column(name="ID_METAJUEGO")
	private int idMetajuego;

	public ArquetipoMetajuego() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdArquetipo() {
		return this.idArquetipo;
	}

	public void setIdArquetipo(int idArquetipo) {
		this.idArquetipo = idArquetipo;
	}

	public int getIdMetajuego() {
		return this.idMetajuego;
	}

	public void setIdMetajuego(int idMetajuego) {
		this.idMetajuego = idMetajuego;
	}

}