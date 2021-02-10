plugins {
    kotlin("jvm") version "1.4.21"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.4.30-RC"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.30-RC"
    id("war")
}

group = "com.joowan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(group = "org.jetbrains.kotlin", name = "kotlin-test-junit5", version = "1.4.10")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect", version = "1.4.21")

    // servlet
    implementation(group = "javax", name = "javaee-api", version = "7.0")

    // spring
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib", version = "1.4.21")
    implementation(group = "org.springframework", name = "spring-webmvc", version = "5.3.3")
    implementation(group = "org.springframework.data", name = "spring-data-jpa", version = "2.4.3")
    implementation(group = "springframework", name = "spring-orm", version = "1.2.6")

    // hibernate
    implementation(group = "org.hibernate", name = "hibernate-core", version = "5.4.27.Final")
    implementation(group = "org.hibernate", name = "hibernate-entitymanager", version = "5.4.27.Final")

    // validation
    implementation(group = "javax.validation", name = "validation-api", version = "2.0.1.Final")
    implementation(group = "org.hibernate.validator", name = "hibernate-validator", version = "7.0.1.Final")
    implementation(
        group = "org.hibernate.validator",
        name = "hibernate-validator-annotation-processor",
        version = "7.0.1.Final"
    )

    // thymeleaf
    implementation(group = "org.thymeleaf", name = "thymeleaf", version = "3.0.11.RELEASE")
    implementation(group = "org.thymeleaf", name = "thymeleaf-spring5", version = "3.0.11.RELEASE")

    // postgreSQL
    implementation(group = "org.postgresql", name = "postgresql", version = "42.2.18")

    // hikariCP
    implementation(group = "com.zaxxer", name = "HikariCP", version = "4.0.1")

    // logging
    implementation(group = "org.slf4j", name = "slf4j-api", version = "1.7.30")
    implementation(group = "org.slf4j", name = "jcl-over-slf4j", version = "1.7.30")
    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
    implementation(group = "ch.qos.logback", name = "logback-core", version = "1.2.3")
}

configurations.all {
    exclude(group = "commons-logging", module = "commons-logging")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}
