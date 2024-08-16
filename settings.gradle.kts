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
include(":androidApp:plan-list")
include(":androidApp:plan-editor")
include(":androidApp:ui")

include(":shared")

include(":shared:core:di")
include(":shared:core:database")
include(":shared:core:clock")

include(":shared:data:plans")
include(":shared:data:trainings")

include(":shared:feature:home")
include(":shared:feature:plan-list")
include(":shared:feature:plan-editor")
include(":shared:core:coroutines")
include(":shared:feature:training-block-details")
include(":androidApp:training-block-details")
