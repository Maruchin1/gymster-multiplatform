plugins {
    alias(libs.plugins.gymster.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.viewmodel)
            api(projects.shared.data.plans)
            api(projects.shared.data.trainings)
            implementation(projects.shared.core.di)
        }
    }
}

android {
    namespace = "com.maruchin.gymster.planlist"
}
