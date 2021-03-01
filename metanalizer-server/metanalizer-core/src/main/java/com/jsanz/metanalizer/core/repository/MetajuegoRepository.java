package com.jsanz.metanalizer.core.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jsanz.metanalizer.core.entity.Metajuego;

@Repository
public interface MetajuegoRepository extends JpaRepository<Metajuego, Integer>{

	@Query("select count(m.id)+1 from Metajuego m")
	public Integer getNextId();

	@Query("select m from Metajuego m where m.ambito.id=:idAmbito and m.consolidado=1 and m.fechaDesde>:inicio and m.fechaHasta<:fin")
	public Metajuego getConsolidadoByAmbitoBetweenFechas(@Param("idAmbito") Integer idAmbito, @Param("inicio") Date inicio, @Param("fin") Date fin);

	@Query("select m from Metajuego m where m.ambito.id=:idAmbito and m.consolidado=1 and m.fechaDesde>:inicio ")
	public Metajuego getConsolidadoByAmbitoBeforeFecha(@Param("idAmbito") Integer idAmbito,@Param("inicio") Date inicio);

	@Query("select m from Metajuego m where m.ambito.id=:idAmbito and m.consolidado=1 and m.fechaHasta<:fin")
	public Metajuego getConsolidadoByAmbitoAfterFecha(@Param("idAmbito") Integer idAmbito, @Param("fin") Date fin);

	@Query("select m from Metajuego m where m.ambito.id=:idAmbito and m.consolidado=1")
	public Metajuego getConsolidadoByAmbito(@Param("idAmbito") Integer idAmbito);


	
	

}
