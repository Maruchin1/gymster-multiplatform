plugins {
    alias(libs.plugins.gymster.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.viewmodel)
            api(projects.shared.data.plans)
            implementation(projects.shared.core.di)
        }
    }
}

android {
    namespace = "com.maruchin.gymster.feature.plans"
}
