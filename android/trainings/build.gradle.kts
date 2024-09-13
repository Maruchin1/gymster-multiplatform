plugins {
    alias(libs.plugins.gymster.android.library)
    alias(libs.plugins.gymster.compose)
}

android {
    namespace = "com.maruchin.gymster.android.trainings"
}

dependencies {
    implementation(projects.shared.feature.trainings)
    implementation(projects.shared.core.utils)
    implementation(projects.android.ui)
}
