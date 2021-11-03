package com.example.routetemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.TemplatedRouteBuilder;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Configuration
public class CamelRouteTemplateAutoConfiguration {

    private final RouteConfigurationProperties routeConfigurationProperties;

    public CamelRouteTemplateAutoConfiguration(RouteConfigurationProperties routeConfigurationProperties) {
        this.routeConfigurationProperties = routeConfigurationProperties;
    }

    @Bean
    CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {

            @SneakyThrows
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                for (File file : getRouteFiles()) {
                    ObjectMapper objectMapper = new XmlMapper();
                    RouteConfiguration route = objectMapper.readValue(file, RouteConfiguration.class);

                    TemplatedRouteBuilder.builder(camelContext, route.getTemplateId())
                            .parameter("routeId", route.getRouteId())
                            .parameters(route.getParameters())
                            .add();

                    log.info("Added a Route: " + route);
                }
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
            }
        };
    }

    private List<File> getRouteFiles() throws Exception {
        final List<File> files = Files.walk(Paths.get(routeConfigurationProperties.getRoutePath()))
                .filter(Files::isRegularFile)
                .filter(file -> file.getFileName().toString().endsWith(".xml"))
                .map(Path::toFile)
                .collect(Collectors.toList());
        log.info("Searched {} Route Properties files... {}", files.size(), files);
        return files;
    }
}
