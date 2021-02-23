package com.jsanz.metanalizer.batch.listener;

import org.slf4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class Log4jExecutionListener implements JobExecutionListener {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("Executing job id " + jobExecution.getId());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
	    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
	    	logger.debug("Terminado job id " + jobExecution.getId() +" correctamente");
	    }else {
	    	logger.debug("Terminado job id " + jobExecution.getId() +" con errores");
	    }
	}
} 