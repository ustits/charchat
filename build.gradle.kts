buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    }
}

allprojects {
    apply(plugin = "kotlin")

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
        kotlinOptions.jvmTarget = "11"
    }

    repositories {
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    application
    alias(libs.plugins.kotlin.serialization)
}

application {
    mainClass.set("charchat.ApplicationKt")
}

group = "charchat"

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("org.flywaydb:flyway-core:8.5.5")
    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverHostCommon)
    implementation(libs.ktor.serverNetty)
    implementation(libs.ktor.serializationJson)
    implementation(libs.ktor.serverResources)
    implementation(libs.ktor.serverWebjars)
    implementation(libs.ktor.serverHtmlBuilder)
    implementation(libs.ktor.serverStatusPages)
    implementation(libs.ktor.serverCallLogging)
    implementation(libs.ktor.serverMetricsMicrometer)
    implementation(libs.ktor.serverAuth)
    implementation(libs.ktor.serverSessions)
    implementation(libs.ktor.serverWebsockets)

    implementation("io.micrometer:micrometer-registry-prometheus:1.8.4")

    implementation("org.webjars.bowergithub.picocss:pico:1.5.0")
    implementation("org.webjars.npm:htmx.org:1.7.0")
    implementation("org.webjars.npm:hyperscript.org:0.9.5")

    implementation("dev.ustits.krefty:krefty-core:0.3.4")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("org.hashids:hashids:1.0.3")

    implementation(libs.hoplite.core)
    implementation(libs.hoplite.toml)

    implementation(libs.logback.classic)

    testImplementation(libs.ktor.serverTests)

    testImplementation(libs.kotest.runnerJUnit5)
    testImplementation(libs.kotest.assertionsCore)
    testImplementation(libs.kotest.extensionsAssertionsKtor)
}
