

package com.springjunky.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.springjunky.hsqlfileserver.HSQLFileDatabaseServer;
import com.springjunky.hsqlfileserver.HSQLFileDatabaseServerImpl;

/**
 */
@Configuration
public class FileDatabaseConfig {


    static Logger logger = LoggerFactory.getLogger(FileDatabaseConfig.class);

    @Autowired
    HSQLFileDatabaseServer batchMetaDB;

    
    @Bean
   // @Primary // Spring's JPA Autoconfig needs a hint :-)
    public DataSource sourceDatabaseDataSource() {
	return batchMetaDB.getBasicDataSourceForFileDatabaseServer();
    }

	
    @ConfigurationProperties(prefix = "batch.meta.data")
    @Bean
    public HSQLFileDatabaseServer batchMetaDataBase() {
	// Just return the Bean the @PostConstruct starts it for you
	return new HSQLFileDatabaseServerImpl();
    }


}
