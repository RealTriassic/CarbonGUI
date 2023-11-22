plugins {
    alias(libs.plugins.shadow)
}

dependencies {
    compileOnly(libs.velocity)
    annotationProcessor(libs.velocity)

    implementation(projects.carbonguiCommon)
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveBaseName.set("${rootProject.name}-Velocity")
        archiveClassifier.set(null as String?)
        mergeServiceFiles()
        minimize()

        relocate("org.spongepowered", "com.triassic.carbongui.libraries.spongepowered")

        doLast {
            copy {
                from(archiveFile)
                into("${rootProject.projectDir}/build")
            }
        }
    }
}
