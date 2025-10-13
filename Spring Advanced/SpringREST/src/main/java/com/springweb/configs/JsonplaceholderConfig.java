package com.springweb.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jsonplaceholder.api")
public class JsonplaceholderConfig {

    private String usersUrl;

    private String commentsUrl;
}
