plugins {
    alias(libs.plugins.gymster.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.viewmodel)
            api(projects.shared.data.trainings)
            implementation(projects.shared.core.utils)
        }
    }
}

android {
    namespace = "com.maruchin.gymster.feature.trainings"
}
