plugins {
    alias(libs.plugins.blossom)
}

dependencies {
    compileOnly(libs.gson)
    compileOnly(libs.slf4j)
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.minimessage)
    implementation(libs.configurate.hocon)
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("version", project.version as String)
            }
        }
    }
}