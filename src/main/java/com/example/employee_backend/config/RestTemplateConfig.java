// package com.example.employee_backend.config;

// public class RestTemplateConfig {
    
// }

//package com.example.demo.config;

package com.example.employee_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
