plugins {
    alias(libs.plugins.gymster.multiplatform)
    alias(libs.plugins.gymster.realm)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.data.plans)
            implementation(projects.shared.core.database)
        }
    }
}

android {
    namespace = "com.maruchin.gymster.data.trainings"
}
