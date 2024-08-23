import org.codehaus.groovy.ast.tools.GeneralUtils.args
import org.gradle.internal.classpath.Instrumented.systemProperty
import org.springframework.boot.gradle.tasks.run.BootRun
import org.gradle.api.tasks.application.*;

buildscript {
    repositories {
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        //classpath 'com.palantir.gradle.docker:gradle-docker:0.28.0'
        classpath ("org.springframework.boot:spring-boot-gradle-plugin:2.5.10")
    }
}
plugins {
    id ("application")
    id ("org.springframework.boot") version ("2.5.10")
    id ("io.spring.dependency-management") version ("1.0.11.RELEASE")
    id("java")
}



application {
    mainClass.set("co.parrolabs.ServiceModelsWebfluxApplication")
}
/*
tasks.named<BootRun>("bootRun") {
    systemProperty("com.example.property", findProperty("example") ?: "default")
}
*/
group = "co.parrolabs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}
apply(plugin = "application")
apply(plugin = "org.springframework.boot")
dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("co.parrolabs:models_dtos:1.0-SNAPSHOT")
    implementation ("org.apache.commons:commons-lang3:3.12.0")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    runtimeOnly ("com.h2database:h2")
    compileOnly ("org.projectlombok:lombok")
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor ("org.projectlombok:lombok")
    implementation ("org.modelmapper:modelmapper:2.4.4")
    implementation ("org.springframework.boot:spring-boot-starter-webflux:2.6.4")
    implementation ("commons-codec:commons-codec:1.15")
    implementation ("org.springframework.data:spring-data-commons:2.7.5")
    implementation ("io.springfox:springfox-swagger-ui:3.0.0")
    implementation ("io.springfox:springfox-boot-starter:3.0.0")
    testImplementation ("org.junit.platform:junit-platform-runner:1.9.2")
    implementation ("commons-io:commons-io:2.11.0")
//    implementation ("org.flywaydb:flyway-core:8.0.2")
    implementation ("mysql:mysql-connector-java:8.0.32")
    implementation ("io.micronaut.data:micronaut-data-model:3.9.6")
    implementation ("org.mongodb:mongodb-driver-sync:4.2.3")
    //implementation ("com.querydsl:querydsl-mongodb:5.0.0")
    implementation ("org.springframework.data:spring-data-mongodb:3.2.3")
    implementation ("org.mongodb:mongodb-driver-core:4.2.3")
    implementation ("org.springframework.kafka:spring-kafka")
    testImplementation ("org.springframework.kafka:spring-kafka-test")
    implementation ("org.springframework.boot:spring-boot-starter-validation:2.4.0")
    implementation ("org.junit.platform:junit-platform-commons:1.8.1")
    testImplementation ("org.mockito:mockito-core:4.11.0")
    implementation ("net.bytebuddy:byte-buddy:1.14.4")
    implementation ("io.vavr:vavr:0.10.3")
    implementation ("org.springframework.boot:spring-boot-starter-security")
    implementation ("org.springframework.cloud:spring-cloud-starter-sleuth:3.0.5")
}

tasks.test {
    useJUnitPlatform()
}

