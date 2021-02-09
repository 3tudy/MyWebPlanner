package com.planner.damotorie.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.PlatformTransactionManager

import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.util.Properties
import javax.sql.DataSource


@Configuration
@PropertySource(value = ["classpath:db.properties"])
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ["com.planner.damotorie"])
open class DBConfig {
    /* JDBC info */
    @Value("\${jdbc.driverClassName}")
    private lateinit var driverClassName: String

    @Value("\${jdbc.url}")
    private lateinit var url: String

    @Value("\${jdbc.username}")
    private lateinit var userName: String

    @Value("\${jdbc.password}")
    private lateinit var password: String

    /* Hibernate 정보 */
    @Value("\${hibernate.dialect}")
    private lateinit var dialect: String

    @Value("\${hibernate.show_sql}")
    private lateinit var showSql: String

    @Value("\${hibernate.format_sql}")
    private lateinit var formatSql: String

    @Value("\${hibernate.hbm2ddl.auto}")
    private lateinit var hbm2ddlAuto: String

    @Bean
    open fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(driverClassName)
        dataSource.url = url
        dataSource.username = userName
        dataSource.password = password
        return dataSource
    }

    private fun hibernateProperties(): Properties {
        val properties = Properties()
        properties["hibernate.dialect"] = dialect
        properties["hibernate.show_sql"] = showSql
        properties["hibernate.format_sql"] = formatSql
        properties["hibernate.hbm2ddl.auto"] = hbm2ddlAuto
        return properties
    }

    @Bean
    open fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource()
        em.setPackagesToScan("com.planner.damotorie")
        val hibernateJpaVendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = hibernateJpaVendorAdapter
        em.setJpaProperties(hibernateProperties())
        return em
    }

    @Bean
    open fun transactionManager(): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory().getObject()
        return transactionManager
    }
}