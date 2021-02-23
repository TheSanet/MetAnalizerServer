package com.jsanz.metanalizer.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jsanz.metanalizer.core.entity.Ambito;
@Repository
public interface AmbitoRepository extends JpaRepository<Ambito, Integer> {

	@Query("select a from Ambito a where a.descripcion=:nombre")
	Ambito getAmbitoByNombre(@Param("nombre")String nombre);

	@Query("select count(a.id)+1 from Ambito a")
	Integer getNextId();

}
