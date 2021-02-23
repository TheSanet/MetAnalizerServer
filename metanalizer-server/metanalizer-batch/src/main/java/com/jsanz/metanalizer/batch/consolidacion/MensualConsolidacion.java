package com.jsanz.metanalizer.batch.consolidacion;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.repository.query.Param;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.jsanz.metanalizer.batch.listener.Log4jExecutionListener;
import com.jsanz.metanalizer.core.entity.Metajuego;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = { "com.jsanz.metanalizer.batch.listener", "com.jsanz.metanalizer.batch.scraping.processor", "com.jsanz.metanalizer.batch.reader" })
@PropertySource("classpath:application.properties")
public class MensualConsolidacion {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	/**
	 * Job principal. Se compone de X step:
	 * 
	 * @param listener
	 * @param step
	 * @return
	 */
	@Bean("mensualConsolidationJob")
	public Job mensualConsolidationJob(Log4jExecutionListener listener,
			@Qualifier("mensualConsolidationStep") Step mensualConsolidationStep) {

		return jobBuilderFactory.get("mensualConsolidationJob").listener(listener).start(mensualConsolidationStep).build();
	}

	// Steps

	/**
	 * @param reader
	 * @param writer
	 * @param processor
	 * @return
	 */
	@Bean(name = "mensualConsolidationStep")
	public Step mensualConsolidationStep(
			@Qualifier("mensualConsolidationReader") ItemReader<Metajuego> mensualConsolidationReader,
			@Qualifier("mensualConsolidationProcess") ItemProcessor<Metajuego, Object> mensualConsolidationProcess,
			@Qualifier("mensualConsolidationWriter") ItemWriter<Object> mensualConsolidationWriter,
			@Qualifier("transactionManagerForStep") PlatformTransactionManager transactionManagerForStep) {

		StepBuilder step = stepBuilderFactory.get("mensualConsolidationStep");
		SimpleStepBuilder<Metajuego, Object> simpleStep = step.<Metajuego, Object>chunk(1);
		simpleStep.reader(mensualConsolidationReader);
		simpleStep.processor(mensualConsolidationProcess);
		simpleStep.writer(mensualConsolidationWriter);
		simpleStep.transactionManager(transactionManagerForStep);
		simpleStep.startLimit(1);
		return simpleStep.build();
	}
	
	@Bean("mensualConsolidationReader")
	public JpaPagingItemReader<Metajuego> mensualConsolidationReader(@Param("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory){
		JpaPagingItemReader<Metajuego> reader = new JpaPagingItemReader<Metajuego>();
	    String sqlQuery = "select * from Metajuego where consolidado=false and FECHA_DESDE>=DATE_SUB(CURDATE(),INTERVAL 10 YEAR) and FECHA_HASTA<=CURDATE() and ID_FORMATO=:idFormato ";
	    JpaNativeQueryProvider<Metajuego> queryProvider = new JpaNativeQueryProvider<Metajuego>();
	    queryProvider.setSqlQuery(sqlQuery);
	    queryProvider.setEntityClass(Metajuego.class);
	    reader.setEntityManagerFactory(entityManagerFactory.getNativeEntityManagerFactory());
	    reader.setPageSize(3);
	    reader.setQueryProvider(queryProvider);
	    
	    Map<String, Object> map=new HashMap<String, Object>();
	    
//	    CAST('2014-07-01' AS DATE)
//	    map.put("fechaDesde", LocalDate.now().minusYears(10));
//	    map.put("fechaHasta", LocalDate.now().plusMonths(1));
	    map.put("idFormato", 1);
	    reader.setParameterValues(map);
	    reader.setSaveState(true);
	    return reader;
		
	}

	@Bean("mensualConsolidationWriter")
	public JpaItemWriter<Object> mensualConsolidationWriter(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		JpaItemWriter<Object> iw= new JpaItemWriter<Object>();
		iw.setEntityManagerFactory(entityManagerFactory);
		return iw;
	}

}
