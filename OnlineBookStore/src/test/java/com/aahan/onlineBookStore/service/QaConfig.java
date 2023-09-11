package com.aahan.onlineBookStore.service;

import com.aahan.onlineBookStore.spring.SpringConfig;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(//
		basePackages = { "com.aahan.onlineBookStore" }, //
		excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, value = { SpringConfig.class })//
)
@PropertySources({ //
		@PropertySource(value = "classpath:./com/aahan/onlineBookStore/test.properties", ignoreResourceNotFound = true) //
})
public class QaConfig {
}