pluginManagement {
    repositories {
        google()
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

rootProject.name = "PokeRogueHelper"
include(":app")
include(":data")
include(":remote")
include(":local")
include(":testing")
include(":analytics")
// libraries
include(":stringmatcher")
 