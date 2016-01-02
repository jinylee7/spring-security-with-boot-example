package com.example.config;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by j-h.lee on 2015-08-27.
 */
@Configuration
public class ApplicationConfig {

    @Autowired
    DataSourceProperties dataSourceProperties;
    DataSource dataSource;

    @Bean
    DataSource realDataSource() {
        DataSourceBuilder factory = DataSourceBuilder
                .create(this.dataSourceProperties.getClassLoader())
                .url(this.dataSourceProperties.getUrl())
                .username(this.dataSourceProperties.getUsername())
                .password(this.dataSourceProperties.getPassword());
        this.dataSource = factory.build();
        return this.dataSource;
    }

    @Bean
    @Primary
    DataSource dataSource() {
        return new Log4jdbcProxyDataSource(this.dataSource);
    }

}
