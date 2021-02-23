package com.jsanz.metanalizer.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.jsanz.metanalizer.core.entity.ArquetipoMetajuego;

public interface ArquetipoMetajuegoRepository extends JpaRepository<ArquetipoMetajuego, Integer>{
	
	@Query("select count(am.id)+1 from ArquetipoMetajuego am")
	public int getNextId();

	@Modifying
	@Transactional
	@Query(value="insert into ArquetipoMetajuego (id,idArquetipo,idMetajuego) values (:id, :idArquetipo, :idMetajuego)",
		   nativeQuery = true)
	public void guardar(@Param("id") Integer id, @Param("idArquetipo") Integer idArquetipo, @Param("idMetajuego") Integer idMetajuego);

}
