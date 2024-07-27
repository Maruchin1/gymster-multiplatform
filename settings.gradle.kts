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
include(":shared")
include(":shared:core:di")
include(":shared:core:database")
include(":shared:data:training-plans")
include(":shared:feature:training-plans")
