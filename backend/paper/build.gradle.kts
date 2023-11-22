import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    alias(libs.plugins.shadow)
    alias(libs.plugins.pluginyml.paper)
    alias(libs.plugins.pluginyml.bukkit)
}

dependencies {
    compileOnly(libs.paper.api)

    compileOnly(libs.carbon)

    implementation(projects.carbonguiCommon)

    implementation(libs.papertrail)
    implementation(libs.fastboard)
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveBaseName.set("${rootProject.name}-Paper")
        archiveClassifier.set(null as String?)
        mergeServiceFiles()
        minimize()

        relocate("org.spongepowered", "com.triassic.carbongui.libraries.spongepowered")
        relocate("io.papermc.papertrail", "com.triassic.carbongui.libraries.papertrail")
        relocate("fr.mrmicky.fastboard", "com.triassic.carbongui.libraries.fastboard")

        doLast {
            copy {
                from(archiveFile)
                into("${rootProject.projectDir}/build")
            }
        }
    }
}

val paperApiVersion = "1.19"

paper {
    name = rootProject.name
    description = project.description
    version = project.version as String
    author = "Triassic"
    main = "com.triassic.carbongui.paper.CarbonGUIPaper"
    serverDependencies {
        register("CarbonChat") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
    }
    foliaSupported = true
    apiVersion = paperApiVersion
}

bukkit {
    name = rootProject.name
    description = project.description
    version = project.version as String
    author = "Triassic"
    main = "com.triassic.carbongui.libs.papertrail.RequiresPaperPlugins"
    apiVersion = paperApiVersion
}
