plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.kotlin.plugin.jpa)
}

group = "ru.sug4chy"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Compile only

    // Implementations
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.telegram.bots)
    implementation(libs.spring.boot.starter.data.jpa)

    implementation(libs.kotlin.reflect)
    implementation(libs.liquibase)

    // Runtime only
    runtimeOnly(libs.postgresql)

    // TEST compile only

    // TEST implementations
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.kotlin.junit5)
    testImplementation(libs.mockk)

    // TEST runtime only
    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.h2)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
