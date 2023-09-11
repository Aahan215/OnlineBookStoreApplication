package com.aahan.onlineBookStore.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan("com.aahan.onlineBookStore")
@PropertySources({ //
		@PropertySource(value = "file:./onlineBookStore.properties", ignoreResourceNotFound = true) //
})
public class SpringConfig {


}
