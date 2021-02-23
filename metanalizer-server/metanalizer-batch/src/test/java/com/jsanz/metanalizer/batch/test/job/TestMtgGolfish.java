package com.jsanz.metanalizer.batch.test.job;



import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.jsanz.metanalizer.batch.config.BatchConfig;
import com.jsanz.metanalizer.batch.scraping.MtgGolfishJobPlan;
import com.jsanz.metanalizer.batch.test.config.TestMtgGolfishConfig;
import com.jsanz.metanalizer.core.config.DatabaseConfig;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DatabaseConfig.class, MtgGolfishJobPlan.class, TestMtgGolfishConfig.class, BatchConfig.class})
public class TestMtgGolfish {
	
	
	@Autowired
	JobLauncherTestUtils jobLauncherTestUtils;
	
	
	@Test
	public void stepScraping() {
		JobExecution jobExecution=jobLauncherTestUtils.launchStep("mtgGolfishScrapingStep", defaultJobParameters());
		assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
		
	    Collection actualStepExecutions = jobExecution.getStepExecutions();
	    ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

	    // then
	    assertEquals(actualStepExecutions.size(), 1);
	    assertEquals(actualJobExitStatus.getExitCode(),"COMPLETED");
	}
	
//	@Test
//	public void jobCompleto() throws Exception {
//		JobExecution jobExecution =jobLauncherTestUtils.launchJob();
//		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
//	}
	
	private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addDate("fechaDeEjecucion", new Date());
        return paramsBuilder.toJobParameters();
   }
	

}
