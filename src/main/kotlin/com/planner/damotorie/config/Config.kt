package com.planner.damotorie.config

import org.hibernate.boot.model.naming.PhysicalNamingStrategy
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring5.view.ThymeleafViewResolver
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ITemplateResolver
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableWebMvc
@EnableJpaRepositories(basePackages = ["com.planner.damotorie"])
@EnableTransactionManagement
@ComponentScan(basePackages = ["com.planner.damotorie"])
open class Config(val applicationContext: ApplicationContext): WebMvcConfigurer {

    val CLASS_RESOURCE_PATH: List<String> = listOf("classpath:/resources/", "classpath:/resources/statics/css/")

    @Bean
    open fun dataSource(): DataSource {
        val dataSource: DriverManagerDataSource = DriverManagerDataSource()
        dataSource.url = "jdbc:h2:mem:userdb;DB_CLOSE_DELAY=-1"
        dataSource.username = "sa"
        dataSource.password = ""
        dataSource.setDriverClassName("org.h2.Driver")

        return dataSource
    }

    @Bean
    open fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val adapter: HibernateJpaVendorAdapter = HibernateJpaVendorAdapter()
        adapter.setGenerateDdl(true)

        val factory = LocalContainerEntityManagerFactoryBean()
        factory.setJpaVendorAdapter(adapter)
        factory.setPackagesToScan("com.planner.damotorie")
        factory.dataSource = dataSource()
        factory.setJpaProperties(jpaProperties())
        return factory
    }

    fun jpaProperties(): Properties {
        val properties:Properties = Properties()

        properties.setProperty("show-sql", "true")
        properties.setProperty("hibernate.naming.physical-strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl")

        return properties
    }

    @Bean
    open fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        val txManager: JpaTransactionManager = JpaTransactionManager()
        txManager.entityManagerFactory = entityManagerFactory
        return txManager
    }

    @Bean
    open fun templateResolver(): ITemplateResolver {
        val templateResolver: SpringResourceTemplateResolver = SpringResourceTemplateResolver()
        templateResolver.setApplicationContext(applicationContext)
        templateResolver.prefix = "resources/view/"
        templateResolver.suffix = ".html"
        templateResolver.templateMode = TemplateMode.HTML
        templateResolver.isCacheable = false
        templateResolver.characterEncoding = "UTF-8"

        return templateResolver
    }

    @Bean
    open fun templateEngine(): SpringTemplateEngine {
        val templateEngine: SpringTemplateEngine = SpringTemplateEngine()
        templateEngine.setTemplateResolver(templateResolver())
        return templateEngine
    }

    @Bean
    open fun viewResolver(): ThymeleafViewResolver {
        val viewResolver: ThymeleafViewResolver = ThymeleafViewResolver()
        viewResolver.templateEngine = templateEngine()
        viewResolver.order = 1
        return viewResolver
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/styles/**").addResourceLocations(CLASS_RESOURCE_PATH[1])
        registry.addResourceHandler("/**").addResourceLocations(CLASS_RESOURCE_PATH[0])
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}