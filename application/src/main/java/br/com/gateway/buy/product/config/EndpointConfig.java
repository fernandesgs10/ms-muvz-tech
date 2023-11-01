package br.com.gateway.buy.product.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class EndpointConfig {

    @Value("${endpoint.url}")
    private String url;

    @Value("${endpoint.context.everthing}")
    private String contextEverthing;

    @Value("${endpoint.context.top.headlines}")
    private String contextTopHeadlines;

    @Value("${endpoint.key}")
    private String key;
}
