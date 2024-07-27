plugins {
    alias(libs.plugins.gymster.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.viewmodel)
            api(projects.shared.data.trainingPlans)
            api(projects.shared.core.di)
        }
    }
}

android {
    namespace = "com.maruchin.multiplatform.gymster.shared.feature.trainingplans"
}
