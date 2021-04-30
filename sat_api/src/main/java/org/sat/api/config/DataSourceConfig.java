package org.sat.api.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean("primaryDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

//    @Bean("secondaryDataSource")
//    @ConfigurationProperties("spring.datasource-clickhouse")
//    public DataSource secondaryDataSource() {
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
//    }

    @Bean(name = "mysqlJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("primaryDataSource") DataSource primaryDataSource) {
        return new JdbcTemplate(primaryDataSource);
    }

//    @Bean(name = "clickHouseJdbcTemplate")
//    public JdbcTemplate clickHouseJdbcTemplate(@Qualifier("secondaryDataSource")
//                                                       DataSource secondaryDataSource) {
//        return new JdbcTemplate(secondaryDataSource);
//    }

}
