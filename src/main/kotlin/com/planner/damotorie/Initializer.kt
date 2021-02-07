package com.planner.damotorie

import com.planner.damotorie.config.Config
import org.h2.server.web.WebServlet
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.web.WebApplicationInitializer
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.request.RequestContextListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.web.filter.DelegatingFilterProxy
import org.springframework.web.servlet.DispatcherServlet
import java.util.*
import javax.servlet.DispatcherType
import javax.servlet.FilterRegistration
import javax.servlet.ServletContext
import javax.servlet.ServletRegistration

class Initializer(): WebApplicationInitializer {

    override fun onStartup(servletContext: ServletContext) {
        val applicationContext: AnnotationConfigWebApplicationContext = AnnotationConfigWebApplicationContext()
        applicationContext.register(Config::class.java)

        servletContext.addListener(ContextLoaderListener(applicationContext))
        servletContext.addListener(RequestContextListener())

        val dispatcher: ServletRegistration.Dynamic = servletContext.addServlet("dispather", DispatcherServlet(applicationContext))
        dispatcher.setLoadOnStartup(1)
        dispatcher.addMapping("/")

        val h2ConsoleServlet: ServletRegistration.Dynamic = servletContext.addServlet("H2Console", WebServlet::class.java)
        h2ConsoleServlet.setInitParameter("-webAllowOthers", "true")
        h2ConsoleServlet.setLoadOnStartup(2)
        h2ConsoleServlet.addMapping("/admin/h2/*")

        val charFilterRegistration: FilterRegistration = servletContext.addFilter("characterEncodingFilter", CharacterEncodingFilter::class.java)
        charFilterRegistration.setInitParameter("encoding", "UTF-8")
        charFilterRegistration.setInitParameter("forceEncoding", "true")
        charFilterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*")

        val securityFilter: FilterRegistration = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy::class.java)
        securityFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true,"/*")
    }
}