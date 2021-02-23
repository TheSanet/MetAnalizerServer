package com.jsanz.metanalizer.batch.scraping;

import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.jsanz.metanalizer.batch.listener.Log4jExecutionListener;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = { "com.jsanz.metanalizer.batch.listener", "com.jsanz.metanalizer.batch.scraping.reader" })
@PropertySource("classpath:application.properties")
public class MtgGolfishJobPlan {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	/**
	 * Job principal. Se compone de 1 step:
	 * 
	 * @param listener
	 * @param step
	 * @return
	 */
	@Bean("mtgGolfishJob")
	public Job mtgGolfishJob(Log4jExecutionListener listener,
			@Qualifier("mtgGolfishScrapingStep") Step mtgGolfishScrapingStep) {

		return jobBuilderFactory.get("mtgGolfishJob").listener(listener).start(mtgGolfishScrapingStep).build();
	}

	// Steps

	/**
	 * @param reader
	 * @param writer
	 * @param processor
	 * @return
	 */
	@Bean(name = "mtgGolfishScrapingStep")
	public Step mtgGolfishScrapingStep(
			@Qualifier("mtgGolfishScrapingReader") ItemReader<Object> mtgGolfishScrapingReader,
			@Qualifier("mtgGolfishScrapingWriter") ItemWriter<Object> mtgGolfishScrapingWriter,
			@Qualifier("transactionManagerForStep") PlatformTransactionManager transactionManagerForStep) {

		StepBuilder step = stepBuilderFactory.get("mtgGolfishScrapingStep");
		SimpleStepBuilder<Object, Object> simpleStep = step.<Object, Object>chunk(1);
		simpleStep.reader(mtgGolfishScrapingReader);
		simpleStep.writer(mtgGolfishScrapingWriter);
		simpleStep.transactionManager(transactionManagerForStep);
		simpleStep.startLimit(1);
		return simpleStep.build();
	}

	@Bean("mtgGolfishScrapingWriter")
	public JpaItemWriter<Object> mtgGolfishScrapingWriter(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		JpaItemWriter<Object> iw= new JpaItemWriter<Object>();
		iw.setEntityManagerFactory(entityManagerFactory);
		return iw;
	}

}
