package com.example.routetemplate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "dna.agent.route")
@Component
@Getter
@Setter
@ToString
public class RouteConfigurationProperties {
    private String routeTemplatePath;
    private String routePath;
}
