package org.activiti.app.ui;

import org.activiti.app.conf.ApplicationConfiguration;
import org.activiti.app.servlet.ApiDispatcherServletConfiguration;
import org.activiti.app.servlet.AppDispatcherServletConfiguration;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Spring Boot 启动类
 *
 * @author <a href="mailto:ferenc.zeng@gmail.com">Ferenc Zeng</a>
 * @since 2019-08-17
 */
@SpringBootApplication(exclude = {
//        HibernateJpaAutoConfiguration.class,
        SecurityAutoConfiguration.class
})
@Import({ApplicationConfiguration.class})
public class ActivitiAppUiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiAppUiApplication.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ActivitiAppUiApplication.class);
    }

    @Bean
    public ServletRegistrationBean appDispatcher() {

        DispatcherServlet appDispatcherServelet = new DispatcherServlet();
        appDispatcherServelet.setContextClass(AnnotationConfigWebApplicationContext.class);
        appDispatcherServelet.setContextConfigLocation(AppDispatcherServletConfiguration.class.getName());

        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(appDispatcherServelet);
        registrationBean.addUrlMappings("/app/*");
        registrationBean.setLoadOnStartup(1);
        registrationBean.setAsyncSupported(true);
        registrationBean.setName("app");

        return registrationBean;
    }

    @Bean
    public ServletRegistrationBean apiDispatcher() {

        DispatcherServlet apiDispatcherServelet = new DispatcherServlet();
        apiDispatcherServelet.setContextClass(AnnotationConfigWebApplicationContext.class);
        apiDispatcherServelet.setContextConfigLocation(ApiDispatcherServletConfiguration.class.getName());

        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(apiDispatcherServelet);
        registrationBean.addUrlMappings("/api/*");
        registrationBean.setLoadOnStartup(1);
        registrationBean.setAsyncSupported(true);
        registrationBean.setName("api");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean openEntityManagerInViewFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new OpenEntityManagerInViewFilter());
        bean.addUrlPatterns("/*");
        bean.setName("openEntityManagerInViewFilter");
        bean.setOrder(-200);
        bean.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC));
        return bean;
    }






}
