package com.ecommerce.FerreViky.configuration;

import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
@Configuration
@Profile("dev")
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource(DataSourceProperties props) {
        DataSource real = props.initializeDataSourceBuilder().build();

        return ProxyDataSourceBuilder.create(real)
                .name("DS-Proxy")
                .logQueryBySlf4j(SLF4JLogLevel.INFO)
                .countQuery()
                .multiline()
                .build();
    }
}