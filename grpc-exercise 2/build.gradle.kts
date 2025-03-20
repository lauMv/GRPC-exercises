import com.google.protobuf.gradle.*

plugins {
    kotlin("jvm") version "1.9.22"
    id("com.google.protobuf") version "0.9.4"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("io.grpc:grpc-netty-shaded:1.60.0")
    implementation("io.grpc:grpc-protobuf:1.60.0")
    implementation("io.grpc:grpc-stub:1.60.0")
    implementation("com.google.protobuf:protobuf-java:3.23.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // gRPC Kotlin Support
    implementation("io.grpc:grpc-kotlin-stub:1.4.1")

    runtimeOnly("com.google.protobuf:protoc:3.23.4")
    runtimeOnly("io.grpc:protoc-gen-grpc-java:1.60.0")
    runtimeOnly("io.grpc:protoc-gen-grpc-kotlin:1.4.1")

    testImplementation(kotlin("test"))

}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.23.4"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.60.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().configureEach {
            plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

application {
    mainClass.set("MainKt") // Ensure this matches your Main.kt file
}
tasks.test {
    useJUnitPlatform()
}