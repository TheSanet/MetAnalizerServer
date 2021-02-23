package com.jsanz.metanalizer.core.test.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.AttributeList;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.jsanz.metanalizer.core.entity.Arquetipo;
import com.jsanz.metanalizer.core.entity.Metajuego;
import com.jsanz.metanalizer.core.entity.Proporcion;
import com.jsanz.metanalizer.core.repository.MetajuegoRepository;
import com.jsanz.metanalizer.core.service.MetajuegoService;
import com.jsanz.metanalizer.core.test.config.ReadOnlyTestConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ReadOnlyTestConfig.class})
public class TestMetajuegoService {

	@Autowired
	public MetajuegoService metajuegoService;
	
	@Test
	public void testGetAll() {
		List<Metajuego> listaMetajuego= metajuegoService.findAll();
		System.out.println(listaMetajuego);
	}
	
	@Test
	public void save() throws IllegalStateException, SecurityException, HeuristicMixedException, HeuristicRollbackException, RollbackException, SystemException {
		Metajuego metajuego= new Metajuego();
		metajuego.setFechaAlta(LocalDate.now());
		metajuego.setFuente(metajuegoService.getFuente(2));
		metajuego.setFormato(metajuegoService.getFormato(1));
		metajuego.setNombre("TEST");
		metajuego.setFechaDesde(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
		metajuego.setFechaHasta(LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()));
		metajuego.setAmbito(metajuegoService.getAmbitoMundial());
		metajuegoService.save(metajuego);
	}
}
