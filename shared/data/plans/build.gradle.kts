plugins {
    alias(libs.plugins.gymster.multiplatform)
    alias(libs.plugins.gymster.realm)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.core.database)
            implementation(projects.shared.core.database2)
            implementation(projects.shared.core.coroutines)
            implementation(projects.shared.core.utils)
        }
    }
}

android {
    namespace = "com.maruchin.gymster.data.plans"
}
