package com.springjunky.hsqlfileserver;

import java.io.File;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.apache.commons.dbcp.BasicDataSource;
import org.hsqldb.Server;
import org.hsqldb.server.ServerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * Implementaion of the simple Database.
 * 
 * @author springjunky
 */
public class HSQLFileDatabaseServerImpl implements HSQLFileDatabaseServer {

    private static final Logger logger = LoggerFactory.getLogger(HSQLFileDatabaseServerImpl.class);

    private static final String ORG_HSQLDB_JDBC_DRIVER = "org.hsqldb.jdbcDriver";

    private static final String EMPTY = "";

    @NotNull
    private Integer serverPort;

    @NotNull
    private Integer dbNumber;

    @NotNull
    private String dbName;


    private Server server;

    @PostConstruct
    public void startServer() {
    if (server == null) {
        logger.info("Starting HSQL server with file Database see target{}HSQLDatabase{}{} ...", File.separator,
            File.separator, dbName);
        server = new Server();
        try {
        server.setPort(serverPort);
        server.setDatabaseName(dbNumber, dbName);
        server.setDatabasePath(dbNumber,
            "file:target" + File.separator + "HSQLDatabase" + File.separator + dbName);
        server.setSilent(false);
        server.start();


        } catch (Exception ex) {
        logger.warn("Exception during startup ", ex);
        }
    }
    }

    @PreDestroy
    public void shutdownServer() {
    if (server.getState() == ServerConstants.SERVER_STATE_ONLINE) {
        server.shutdown();
        logger.info("HSQL server at target{}HSQLDatabase{}{} stopped", File.separator, File.separator, dbName);
    }
    }

    /*
     * Get your jdbc-Connection-String
     */
    @Override
    public DataSource getBasicDataSourceForFileDatabaseServer() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName(ORG_HSQLDB_JDBC_DRIVER);
    String jdbcConnectionString = this.getJdbcConnectionString();
    dataSource.setUrl(jdbcConnectionString);
    logger.info("************************************************************************");
    logger.info("*                                                                       ");
    logger.info("*                                                                       ");
    logger.info("JDBC-Connection-String {}", jdbcConnectionString);
    logger.info("*                                                                       ");
    logger.info("*                                                                       ");
    logger.info("*                                                                       ");
    logger.info("************************************************************************");

    return dataSource;
    }

    @Override
    public String getJdbcConnectionString() {
    String hostName;
    try {
        hostName = Inet4Address.getLocalHost().getHostName();
    } catch (UnknownHostException ex) {
        hostName = "unknown";
        logger.warn("Your hostname freaks out ", ex);
    }
    return "jdbc:hsqldb:hsql://" + hostName + ":" + serverPort + "/" + dbName;
    }


    // All the boring setter / getter need by @ConfigurationProperties for a
    // type-safe binding
    public Integer getServerPort() {
    return serverPort;
    }

    public void setServerPort(Integer serverPort) {
    this.serverPort = serverPort;
    }

    public Integer getDbNumber() {
    return dbNumber;
    }

    public void setDbNumber(Integer dbNumber) {
    this.dbNumber = dbNumber;
    }

    public String getDbName() {
    return dbName;
    }

    public void setDbName(String dbName) {
    this.dbName = dbName;
    }


}
