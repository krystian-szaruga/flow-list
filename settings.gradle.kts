pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "FlowList"
include(":app")
include(":feature:task")
include(":feature:project")
include(":feature:settings")
include(":core:ui")
include(":core:notification")
include(":data")
include(":domain")
include(":core-impl")
include(":core:viewmodel")
include(":feature:recurring")
include(":security")
include(":core:resources")
