package com.ding.spring.spring2020study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;


import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, JdbcTemplateAutoConfiguration.class})
public class Spring2020StudyApplication implements CommandLineRunner {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Spring2020StudyApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(Spring2020StudyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("run-000000000");

    }

    @Primary
    @Bean
    @ConfigurationProperties("foo.datasource")
    public DataSourceProperties getFooDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource fooDataSource() {
        DataSourceProperties dataSourceProperties = getFooDataSourceProperties();
        log.info("getFooDataSource.url:{}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Resource
    public PlatformTransactionManager fooManager(DataSource fooDataSource) {
        try {
            log.info("fooManager.PlatformTransactionManager.url:{}", fooDataSource.getConnection().getMetaData().getURL());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new DataSourceTransactionManager(fooDataSource);
    }
//////////////////////////////////////////

    @Bean
    @ConfigurationProperties("bar.datasource")
    public DataSourceProperties barDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource barDataSource() {
        DataSourceProperties dataSourceProperties = barDataSourceProperties();
        log.info("bar datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Resource
    public PlatformTransactionManager barTxManager(DataSource barDataSource) {
        try {
            log.info("barTxManager.PlatformTransactionManager.url:{}", barDataSource.getConnection().getMetaData().getURL());

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return new DataSourceTransactionManager(barDataSource);
    }

}
