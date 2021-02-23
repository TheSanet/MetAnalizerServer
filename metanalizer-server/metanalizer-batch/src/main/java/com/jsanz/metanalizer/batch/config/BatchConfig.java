package com.jsanz.metanalizer.batch.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

	public DefaultBatchConfigurer myDefaultBatchConfigurer(@Qualifier("dataSource") DataSource dataSource) {
		return new DefaultBatchConfigurer(dataSource);
	}
	
	@Bean("transactionManagerForStep")
	public PlatformTransactionManager transactionManagerForStep(@Qualifier("entityManagerFactory")LocalContainerEntityManagerFactoryBean entityManagerFactory, @Qualifier("dataSource") DataSource dataSource) throws SQLException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setDataSource(dataSource);
		transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
		return transactionManager;
	}

}
