package com.jsanz.metanalizer.batch.test.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestTcDecksConfig {
	
	@Bean
    public JobLauncherTestUtils getJobLauncherTestUtils(){

           return new JobLauncherTestUtils() {
               @Override
               @Autowired
               public void setJob(@Qualifier("tcDecksJob") Job job) {
                   super.setJob(job);
               }
           };
       } 

}
