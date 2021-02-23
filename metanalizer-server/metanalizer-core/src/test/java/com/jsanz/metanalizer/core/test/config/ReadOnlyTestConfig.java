package com.jsanz.metanalizer.core.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.jsanz.metanalizer.core.config.DatabaseConfig;

@Configuration
@Import({ DatabaseConfig.class })
public class ReadOnlyTestConfig {

}
