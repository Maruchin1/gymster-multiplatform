plugins {
    alias(libs.plugins.gymster.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.datastore)
        }
    }
}

android {
    namespace = "com.maruchin.gymster.core.datastore"
}
