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

include(":android:app")
include(":android:home")
include(":android:plans")
include(":android:trainings")
include(":android:ui")

include(":shared:umbrella")

include(":shared:core:database")
include(":shared:core:clock")
include(":shared:core:coroutines")
include(":shared:core:settings")
include(":shared:core:utils")

include(":shared:data:plans")
include(":shared:data:trainings")

include(":shared:feature:home")
include(":shared:feature:plans")
include(":shared:feature:trainings")
