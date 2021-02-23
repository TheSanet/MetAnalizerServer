package com.jsanz.metanalizer.batch.scraping.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.jsanz.metanalizer.core.entity.Metajuego;

@Component
public class MensualConsolidationProcess implements ItemProcessor<Metajuego, Object> {

	@Override
	public Object process(Metajuego item) throws Exception {
		System.out.println(item);
		return null;
	}

}
