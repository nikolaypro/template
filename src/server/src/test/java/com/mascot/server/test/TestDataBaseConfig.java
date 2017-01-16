package com.mascot.server.test;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.ejb.HibernatePersistence;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Nikolay on 11.01.2017.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.mascot.server.beans")
@PropertySource("classpath:test.properties")
public class TestDataBaseConfig {
    public static final String PROP_DATABASE_DRIVER = "db.driver";
    public static final String PROP_DATABASE_USERNAME = "db.username";
    public static final String PROP_DATABASE_PASSWORD = "db.password";
    public static final String PROP_DATABASE_URL = "db.url";
    public static final String PROP_DATABASE_NAME = "db.database";

    @Resource
    private Environment env;

    @Resource
    private ResourceLoader resourceLoader;

    @Bean
    @DependsOn("testDatabase")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceXmlLocation("META-INF/persistence.xml");
        entityManagerFactoryBean.setPersistenceUnitName("templatePersistenceUnit");
        entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
        entityManagerFactoryBean.setValidationMode(ValidationMode.NONE);

        entityManagerFactoryBean.setDataSource(dataSource());
/*
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN);

        entityManagerFactoryBean.setJpaProperties(hibernateProp());
*/

        return entityManagerFactoryBean;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityMSManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceXmlLocation("META-INF/persistence.xml");
        entityManagerFactoryBean.setPersistenceUnitName("templateMSPersistenceUnit");
        entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());

        entityManagerFactoryBean.setDataSource(dataSource());
/*
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN);

        entityManagerFactoryBean.setJpaProperties(hibernateProp());
*/

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(PROP_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROP_DATABASE_URL) + env.getRequiredProperty(PROP_DATABASE_NAME));
        dataSource.setUsername(env.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));
        return dataSource;
    }

    @Bean
    public TestDatabase testDatabase() {
        createDatabaseAndTables();
        return new TestDatabase();
    }

    public void createDatabaseAndTables() {
        try {
            new TestDatabase().createDb(env);

            final DataSource dataSource = dataSource();
            SpringLiquibase liquibase = new SpringLiquibase();
            liquibase.setDataSource(dataSource);
            liquibase.setChangeLog("src/db/liquibase/update.xml");
            liquibase.setResourceLoader(new FileSystemResourceLoader());
            liquibase.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
