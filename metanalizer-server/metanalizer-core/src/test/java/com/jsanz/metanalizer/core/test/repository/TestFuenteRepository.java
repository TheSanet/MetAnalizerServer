package com.jsanz.metanalizer.core.test.repository;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.jsanz.metanalizer.core.entity.Fuente;
import com.jsanz.metanalizer.core.entity.Metajuego;
import com.jsanz.metanalizer.core.repository.FuenteRepository;
import com.jsanz.metanalizer.core.test.config.ReadOnlyTestConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ReadOnlyTestConfig.class})
public class TestFuenteRepository {
	
	@Autowired
	public FuenteRepository fuenteRepository;
	
	@Test
	public void testCreaUpdate() {
		Fuente fuente=new Fuente();
		fuente.setId(2);
		fuente.setFechaAlta(new Date());
		fuente.setNombre("UPDATE");
		fuenteRepository.save(fuente);
	}

}
