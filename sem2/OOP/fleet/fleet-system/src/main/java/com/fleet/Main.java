package com.fleet;
import com.fleet.servlets.VehicleServlet;
import jakarta.servlet.Servlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;


public class Main {
    public static void main(String[] args) throws LifecycleException {
        String contextPath = "/fleet"; // Context path of your servlet
        String servletMapping = "/vehicles"; // URL pattern of your servlet

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080); // Port number for Tomcat

        Context context = tomcat.addContext(contextPath, null);


        VehicleServlet vehicleServlet = new VehicleServlet();



        tomcat.addServlet(contextPath, "VehicleServlet", VehicleServlet.class.getName());
        context.addServletMappingDecoded("/vehicleServlet", "VehicleServlet");



        tomcat.start();
        tomcat.getConnector();
        tomcat.getServer().await();

    }
}