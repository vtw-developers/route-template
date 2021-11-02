package com.example.routetemplate;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@JacksonXmlRootElement(localName = "route")
@Getter
@Setter
@ToString
public class RouteConfiguration {

    private String templateId;
    private String routeId;
    private Map<String, Object> parameters;
}
