//buildscript {
//    ext {
//        kotlinVersion = '1.4.21'
//        springVersion = '5.3.3'
//    }
//
//    repositories {
//        mavenCentral()
//        jcenter()
//    }
//
//    dependencies {
//        classpath "org.jetbrains.kotlin:kotlin-noarg:$kotlinVersion"
//    }
//
//}
//
//plugins {
//    id 'java'
//    id 'org.jetbrains.kotlin.jvm' version '1.4.21'
//    id "org.jetbrains.kotlin.plugin.jpa" version "1.3.61"
//}
//
//group 'org.example'
//version '1.0-SNAPSHOT'
//
//repositories {
//    mavenCentral()
//}
//
//dependencies {
//    implementation "org.jetbrains.kotlin:kotlin-stdlib"
//    implementation "org.springframework:spring-webmvc:5.3.3"
//    implementation "org.springframework.data:spring-data-jpa:2.4.3"
//    implementation "org.hibernate:hibernate-entitymanager:5.4.10.Final"
//    implementation "org.thymeleaf:thymeleaf:3.0.11.RELEASE"
//    implementation "org.thymeleaf:thymeleaf-spring5:3.0.11.RELEASE"
//    implementation "javax.servlet:javax.servlet-api:3.1.0"
//    implementation "com.h2database:h2:1.4.200"
//    implementation "org.jetbrains.kotlin:kotlin-reflect:1.4.21"
//    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.6.0'
//    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine'
//}
//
//test {
//    useJUnitPlatform()
//}

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
    implementation(group="javax", name="javaee-api", version="7.0")

    // spring
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib", version = "1.4.21")
    implementation(group = "org.springframework", name = "spring-webmvc", version = "5.3.3")
    implementation(group = "org.springframework.data", name = "spring-data-jpa", version = "2.4.3")
    implementation(group = "springframework", name = "spring-orm", version = "1.2.6")

    // hibernate
    implementation(group = "org.hibernate", name = "hibernate-core", version = "5.4.27.Final")
    implementation(group = "org.hibernate", name = "hibernate-entitymanager", version = "5.4.27.Final")

    // thymeleaf
    implementation(group = "org.thymeleaf", name = "thymeleaf", version = "3.0.11.RELEASE")
    implementation(group = "org.thymeleaf", name = "thymeleaf-spring5", version = "3.0.11.RELEASE")

    // h2
    implementation(group = "com.h2database", name = "h2", version = "1.4.200")

    // postgreSQL
//    implementation(group = "org.postgresql", name = "postgresql", version = "42.2.18")

}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}
