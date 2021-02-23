package com.jsanz.metanalizer.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jsanz.metanalizer.core.entity.Arquetipo;
import com.jsanz.metanalizer.core.entity.Proporcion;

@Repository
public interface ProporcionRepository extends JpaRepository<Proporcion, Integer>{
	
	@Query("select count(p.id)+1 from Proporcion p")
	public int getNextId();

	public default void saveAndFlush(List<Proporcion> listaProporcion) {
			for(Proporcion a:listaProporcion) {
				saveAndFlush(a);
			}
		
	}

	@Query("select p from Proporcion p wher p.idArquetipo=:idArquetipo and p.idMetajuego=:idMetajuego")
	public Proporcion getProporcionByIdMetajuegoIdArquetipo( @Param("idMetajuego")Integer idMetajuego,
			@Param("idArquetipo")Integer idArquetipo);

}
