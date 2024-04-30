package com.fleet;
import com.fleet.resolvers.CustomKeycloakConfigResolver;
import com.fleet.servlets.LoginServlet;
import jakarta.servlet.http.HttpServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import com.fleet.servlets.VehicleServlet;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import com.fleet.filters.CorsFilter;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.keycloak.adapters.servlet.KeycloakOIDCFilter;

import java.io.File;
import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws LifecycleException, MalformedURLException {
        String contextPath = "/fleet"; // Context path of your servlet

        Tomcat tomcat = new Tomcat();

        tomcat.setPort(8080); // Port number for Tomcat

        Context context = tomcat.addContext("", null);

        // Instantiate your servlet
        // Context contextWebApp = tomcat.addWebapp("app", new File("fleet-system/src/main/webapp").getAbsolutePath());

        context.setParentClassLoader(VehicleServlet.class.getClassLoader());



        CorsFilter corsFilter = new CorsFilter();
        FilterDef filterDef = new FilterDef();
        filterDef.setFilterName("CorsFilter");
        filterDef.setFilterClass(corsFilter.getClass().getName());
        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName("CorsFilter");
        filterMap.addURLPattern("/*");


        context.addFilterDef(filterDef);
        context.addFilterMap(filterMap);

        KeycloakOIDCFilter keycloakFilter = new KeycloakOIDCFilter();

        FilterDef keycloakFilterDef = new FilterDef();
        keycloakFilterDef.setFilterName("keycloak");
        keycloakFilterDef.setFilterClass(keycloakFilter.getClass().getName());
        keycloakFilterDef.addInitParameter("keycloak.config.resolver", CustomKeycloakConfigResolver.class.getName());

        FilterMap keycloakFilterMap = new FilterMap();
        keycloakFilterMap.setFilterName("keycloak");

        keycloakFilterMap.addURLPattern("/login/*");


        context.addFilterDef(keycloakFilterDef);
        context.addFilterMap(keycloakFilterMap);


        // Add your servlet to the context
        tomcat.addServlet("", "VehicleServlet", new VehicleServlet());
        tomcat.addServlet("", "LoginServlet", new LoginServlet());



        // Map your servlet to the context path
        context.addServletMappingDecoded("/vehicles/*", "VehicleServlet");
        context.addServletMappingDecoded("/login/*", "LoginServlet");



        tomcat.start();
        tomcat.getConnector();
        tomcat.getServer().await();
    }


}
