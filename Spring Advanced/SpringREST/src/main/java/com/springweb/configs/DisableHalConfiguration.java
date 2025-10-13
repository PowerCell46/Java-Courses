package com.springweb.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@Configuration
@EnableHypermediaSupport(type = {})  // This disables HAL globally
public class DisableHalConfiguration {
}
