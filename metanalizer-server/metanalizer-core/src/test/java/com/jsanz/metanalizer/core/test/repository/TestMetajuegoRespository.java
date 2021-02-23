package com.jsanz.metanalizer.core.test.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.jsanz.metanalizer.core.entity.Metajuego;
import com.jsanz.metanalizer.core.repository.MetajuegoRepository;
import com.jsanz.metanalizer.core.test.config.ReadOnlyTestConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ReadOnlyTestConfig.class})
public class TestMetajuegoRespository {
	
	@Autowired
	public MetajuegoRepository metajuegoRepository;
	
	@Test
	public void testGetAll() {
		List<Metajuego> listaMetajuego= metajuegoRepository.findAll();
		System.out.println(listaMetajuego);
	}

}
