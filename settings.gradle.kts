@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "CarbonGUI"

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        exclusiveContent {
            forRepository {
                maven("https://api.modrinth.com/maven")
            }

            filter {
                includeGroup("maven.modrinth")
            }
        }
    }
}

val backendProjects = listOf(
    "common" to "carbongui-common",
    "paper" to "carbongui-paper"
)

include("carbongui-velocity")
project(":carbongui-velocity").projectDir = file("velocity")

backendProjects.forEach { (dir, projectName) ->
    include(projectName)
    project(":$projectName").projectDir = file("backend/$dir")
}