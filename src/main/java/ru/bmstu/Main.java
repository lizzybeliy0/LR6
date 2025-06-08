package ru.bmstu;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.bmstu.config.AppConfig;

public class Main {
    public static void main(String[] args) throws Exception {
        // Создаем сервер Jetty
        Server server = new Server(8081);

        // Создаем контекст Spring
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        // Создаем DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

        // Создаем контекст Jetty
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");

        // Добавляем DispatcherServlet
        contextHandler.addServlet(new ServletHolder(dispatcherServlet), "/*");

        // Устанавливаем контекст серверу
        server.setHandler(contextHandler);

        // Запускаем сервер
        server.start();
        System.out.println("Server started at http://localhost:8081");
        server.join();
    }
}