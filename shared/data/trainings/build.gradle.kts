plugins {
    alias(libs.plugins.gymster.multiplatform)
    alias(libs.plugins.gymster.realm)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.shared.data.plans)
            implementation(projects.shared.core.database)
            implementation(projects.shared.core.coroutines)
        }
    }
}

android {
    namespace = "com.maruchin.gymster.data.trainings"
}
