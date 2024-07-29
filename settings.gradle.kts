enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("convention-plugins")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "gymster-multiplatform"
include(":androidApp")
include(":androidApp:home")
include(":androidApp:plans")
include(":androidApp:ui")

include(":shared")
include(":shared:core:di")
include(":shared:core:database")
include(":shared:data:plans")
include(":shared:data:trainings")
include(":shared:feature:plans")
include(":shared:feature:trainings")

