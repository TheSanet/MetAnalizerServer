package com.jsanz.metanalizer.batch.scraping;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.PlatformTransactionManager;

import com.jsanz.metanalizer.batch.listener.Log4jExecutionListener;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = { "com.jsanz.metanalizer.batch.listener", "com.jsanz.metanalizer.batch.scraping.reader" })
@PropertySource("classpath:application.properties")
public class TcDecksJobPlan {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	/**
	 * Job principal. Se compone de 2 step:
	 * 
	 * @param listener
	 * @param step
	 * @return
	 */
	@Bean("tcDecksJob")
	public Job mtgGolfishJob(Log4jExecutionListener listener,
			@Qualifier("tcDecksScrapingStep") Step tcDecksScrapingStep) {

		return jobBuilderFactory.get("tcDecksJob").listener(listener).start(tcDecksScrapingStep).build();
	}

	// Steps

	/**
	 * @param reader
	 * @param writer
	 * @param processor
	 * @return
	 */
	@Bean(name = "tcDecksScrapingStep")
	public Step mtgGolfishScrapingStep(
			@Qualifier("tcDecksScrapingReader") ItemReader<Object> tcDecksScrapingReader,
			@Qualifier("tcDecksScrapingWriter") ItemWriter<Object> tcDecksScrapingWriter,
			@Qualifier("transactionManagerForStep") PlatformTransactionManager transactionManagerForStep) {

		StepBuilder step = stepBuilderFactory.get("tcDecksScrapingStep");
		SimpleStepBuilder<Object, Object> simpleStep = step.<Object, Object>chunk(1);
		simpleStep.reader(tcDecksScrapingReader);
		simpleStep.writer(tcDecksScrapingWriter);
		simpleStep.transactionManager(transactionManagerForStep);
		simpleStep.startLimit(1);
		return simpleStep.build();
	}

	@Bean("tcDecksScrapingWriter")
	public JpaItemWriter<Object> mtgGolfishScrapingWriter(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		JpaItemWriter<Object> iw= new JpaItemWriter<Object>();
		iw.setEntityManagerFactory(entityManagerFactory);
		return iw;
	}
	


}
