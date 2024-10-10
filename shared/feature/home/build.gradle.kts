plugins {
    alias(libs.plugins.gymster.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.viewmodel)
        }
    }
}

android {
    namespace = "com.maruchin.gymster.feature.home"
}
