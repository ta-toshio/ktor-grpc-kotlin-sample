import com.google.protobuf.gradle.id

val kotlin_version: String by project
val logback_version: String by project
val grpc_kotlin: String by project
val grpc_version : String by project
val protobuf_version : String by project

plugins {
    kotlin("jvm") version "2.1.0"
    id("io.ktor.plugin") version "3.0.2"

    // https://plugins.gradle.org/plugin/com.google.protobuf
    id("com.google.protobuf") version "0.9.4"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // ktor default
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml-jvm")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // gRPC
    implementation("io.grpc:grpc-kotlin-stub:$grpc_kotlin") // https://mvnrepository.com/artifact/io.grpc/grpc-kotlin-stub
    implementation("io.grpc:grpc-protobuf:$grpc_version") // https://mvnrepository.com/artifact/io.grpc/grpc-protobuf
    implementation("io.grpc:grpc-netty:$grpc_version") // https://mvnrepository.com/artifact/io.grpc/grpc-netty
    implementation("io.grpc:grpc-services:$grpc_version") // https://mvnrepository.com/artifact/io.grpc/grpc-netty

    // protobuf
    implementation("com.google.protobuf:protobuf-kotlin:$protobuf_version") // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-kotlin

}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobuf_version"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpc_version"
        }

        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpc_kotlin:jdk8@jar" // https://mvnrepository.com/artifact/io.grpc/protoc-gen-grpc-kotlin
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {task ->
            task.plugins {
                id("grpc") {}
                id("grpckt") {}
            }

            task.builtins {
                id("kotlin")
            }
        }
    }
}
