//package com.st.dream.controller;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import liquibase.integration.spring.SpringLiquibase;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import javax.sql.DataSource;
//
//@Profile("!test")
//@Configuration
//public class MysqlConfig {
//
//    @Bean(destroyMethod="")
//    public DataSource jndiDataSource() throws IllegalArgumentException {
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
//        hikariConfig.setMaximumPoolSize(properties.getDatabase().getPoolMaxActive());
//        hikariConfig.setMinimumIdle(properties.getDatabase().getPoolMinIdle());
//        hikariConfig.setUsername(properties.getDatabase().getUsername());
//        hikariConfig.setPassword(properties.getDatabase().getPassword());
//        hikariConfig.setAutoCommit(true);
//        hikariConfig.setConnectionTimeout(properties.getDatabase().getTimeout());
//        hikariConfig.setJdbcUrl(String.format("%s/%s", properties.getDatabase().getHost(),properties.getDatabase().getSchema()));
//        hikariConfig.addDataSourceProperty("useSSL", "false");
//        return new HikariDataSource(hikariConfig);
//    }
//
//
//    @Bean(name = "liquibase")
//    public SpringLiquibase liquibase(DataSource dataSource)  {
//        SpringLiquibase springLiquibase = new SpringLiquibase();
//        springLiquibase.setDataSource(dataSource);
//        springLiquibase.setChangeLog("classpath:liquibase/MasterDatabaseChangeLog.xml");
//        springLiquibase.setContexts(properties.getEnvironment());
//        return springLiquibase;
//    }
//}
