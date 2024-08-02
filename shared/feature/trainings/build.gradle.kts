plugins {
    alias(libs.plugins.gymster.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.viewmodel)
            api(projects.shared.data.trainings)
            implementation(projects.shared.core.di)
            implementation(projects.shared.core.clock)
        }
    }
}

android {
    namespace = "com.maruchin.gymster.feature.trainings"
}
