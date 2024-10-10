plugins {
    alias(libs.plugins.gymster.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.shared.data.plans)
            implementation(projects.shared.core.database)
            implementation(projects.shared.core.utils)
        }
    }
}

android {
    namespace = "com.maruchin.gymster.data.trainings"
}
