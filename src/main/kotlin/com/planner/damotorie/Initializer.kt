package com.planner.damotorie

import com.planner.damotorie.config.Config
import com.planner.damotorie.config.DBConfig
import org.springframework.web.WebApplicationInitializer
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.web.servlet.DispatcherServlet
import javax.servlet.FilterRegistration
import javax.servlet.ServletContext
import javax.servlet.ServletRegistration

class Initializer(): WebApplicationInitializer {

    override fun onStartup(servletContext: ServletContext) {
        val applicationContext: AnnotationConfigWebApplicationContext = AnnotationConfigWebApplicationContext()
        applicationContext.register(Config::class.java, DBConfig::class.java)

        servletContext.addListener(ContextLoaderListener(applicationContext))

        val dispatcher: ServletRegistration.Dynamic = servletContext.addServlet("dispather", DispatcherServlet(applicationContext))
        dispatcher.setLoadOnStartup(1)
        dispatcher.addMapping("/")


        val charFilterRegistration: FilterRegistration = servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter::class.java)
        charFilterRegistration.setInitParameter("encoding", "UTF-8")
        charFilterRegistration.setInitParameter("forceEncoding", "true")
        charFilterRegistration.addMappingForUrlPatterns(null, true, "/*")
    }
}