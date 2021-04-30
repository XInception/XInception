package org.sat.api.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryPrimary",
        transactionManagerRef="transactionManagerPrimary",
        basePackages= { "org.sat.api.entity" })
@Slf4j
public class MysqlDataSource {


    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;

    @Autowired
    private JpaProperties jpaProperties; //Jpa自带的一个读取jpa属性的类

    @Autowired
    HibernateProperties hibernateProperties1;


    @Bean("entityManagerFactoryPrimary")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false); //hibernate基本配置
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setShowSql(true);


        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.dj.iotlite.entity"); //实体扫描
        factory.setDataSource(primaryDataSource);
        jpaProperties.getProperties().put("hibernate.jdbc.batch_size", "100");
        jpaProperties.getProperties().put("hibernate.jdbc.batch_versioned_data", "true");
        jpaProperties.getProperties().put("hibernate.order_inserts", "true");
        jpaProperties.getProperties().put("hibernate.order_updates ", "true");

        log.info("{} ",jpaProperties.getProperties().get("hibernate.jdbc.batch_size"));
        Map<String, Object> hibernateProperties = hibernateProperties1.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());

        factory.setJpaPropertyMap(hibernateProperties);
        return factory;

    }


    @Bean("transactionManagerPrimary")
    @Primary
    public PlatformTransactionManager transactionManagerPrimary() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactoryPrimary().getObject());
        return txManager;
    }
}
