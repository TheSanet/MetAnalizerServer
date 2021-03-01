package com.jsanz.metanalizer.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jsanz.metanalizer.core.entity.Arquetipo;

@Repository
public interface ArquetipoRepository extends JpaRepository<Arquetipo, Integer>{
	@Query("select count(a.id)+1 from Arquetipo a")
	public int getNextId();

	@Query("select count(am.id)+1 from ArquetipoMetajuego am")
	public int getNextArquetipoMetajuegoId();

	@Query("select a from Arquetipo a, ArquetipoMetajuego am where am.idArquetipo=a.id and am.idMetajuego=:idMetajuego ")
	public List<Arquetipo> getByMetajuegoId(@Param("idMetajuego") Integer idMetajuego);
	
	
	








}
