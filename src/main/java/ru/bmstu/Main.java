package ru.bmstu;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.bmstu.config.AppConfig;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8081);
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/");

        contextHandler.addServlet(new ServletHolder(dispatcherServlet), "/*");

        server.setHandler(contextHandler);

        server.start();
        System.out.println("Server started at http://localhost:8081/api/v1");
        server.join();
    }
}