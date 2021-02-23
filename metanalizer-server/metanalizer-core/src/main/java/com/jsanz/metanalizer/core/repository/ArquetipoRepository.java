package com.jsanz.metanalizer.core.repository;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jsanz.metanalizer.core.entity.Arquetipo;

@Repository
public interface ArquetipoRepository extends JpaRepository<Arquetipo, Integer>{
	@Query("select count(a.id)+1 from Arquetipo a")
	public int getNextId();

	@Query("select count(am.id)+1 from ArquetipoMetajuego am")
	public int getNextArquetipoMetajuegoId();
	
	
	








}
