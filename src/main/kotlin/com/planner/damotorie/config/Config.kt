package com.planner.damotorie.config

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.validation.Validator
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring5.view.ThymeleafViewResolver
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ITemplateResolver


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = ["com.planner.damotorie"])
open class Config(private val applicationContext: ApplicationContext): WebMvcConfigurer {

//    private val CLASS_RESOURCE_PATH: List<String> = listOf("classpath:/resources/", "classpath:/resources/statics/css/")
//
//    @Bean
//    open fun templateResolver(): ITemplateResolver {
//        val templateResolver: SpringResourceTemplateResolver = SpringResourceTemplateResolver()
//        templateResolver.setApplicationContext(applicationContext)
//        templateResolver.prefix = "resources/view/"
//        templateResolver.suffix = ".html"
//        templateResolver.templateMode = TemplateMode.HTML
//        templateResolver.isCacheable = false
//        templateResolver.characterEncoding = "UTF-8"
//
//        return templateResolver
//    }
//
//    @Bean
//    open fun templateEngine(): SpringTemplateEngine {
//        val templateEngine: SpringTemplateEngine = SpringTemplateEngine()
//        templateEngine.setTemplateResolver(templateResolver())
//        return templateEngine
//    }
//
//    @Bean
//    open fun viewResolver(): ThymeleafViewResolver {
//        val viewResolver: ThymeleafViewResolver = ThymeleafViewResolver()
//        viewResolver.templateEngine = templateEngine()
//        viewResolver.order = 1
//        return viewResolver
//    }
//
//    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
//        registry.addResourceHandler("/styles/**").addResourceLocations(CLASS_RESOURCE_PATH[1])
//        registry.addResourceHandler("/**").addResourceLocations(CLASS_RESOURCE_PATH[0])
//    }
}