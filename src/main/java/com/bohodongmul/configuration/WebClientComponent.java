package com.bohodongmul.configuration;

import java.time.Duration;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;

@Configuration
public class WebClientComponent implements DisposableBean {
	
    private LoopResources loopResources;
    private ConnectionProvider connectionProvider;
	
	@Bean
    DefaultUriBuilderFactory builderFactory(){
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        return factory;
    }

    @Bean
    WebClient webClient(){
        this.loopResources = LoopResources.create("webClient-loop", 2, true);
        this.connectionProvider = ConnectionProvider.builder("webClient-connection")
        	    .maxConnections(50)
        	    .pendingAcquireTimeout(Duration.ofSeconds(30))
        	    .maxIdleTime(Duration.ofSeconds(20))
        	    .build();
        
        return WebClient.builder()
                .uriBuilderFactory(builderFactory())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                .clientConnector(new ReactorClientHttpConnector(
                		HttpClient.create(connectionProvider).runOn(loopResources)))
                .build();
    }

	@Override
	public void destroy() throws Exception {		
        if (loopResources != null) {
            loopResources.disposeLater().block();
        }
        
        if (connectionProvider != null) {
            connectionProvider.disposeLater().block();
        }
		
	}
}
