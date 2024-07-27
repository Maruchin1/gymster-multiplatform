plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("multiplatform") {
            id = "gymster.multiplatform"
            implementationClass = "MultiplatformConventionPlugin"
        }
        register("realm") {
            id = "gymster.realm"
            implementationClass = "RealmConventionPlugin"
        }
    }
}
