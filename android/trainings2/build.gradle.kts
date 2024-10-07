plugins {
    alias(libs.plugins.gymster.android.library)
    alias(libs.plugins.gymster.compose)
}

android {
    namespace = "com.maruchin.gymster.android.trainings2"
}

dependencies {
    implementation(projects.shared.feature.trainings2)
    implementation(projects.shared.core.utils)
    implementation(projects.android.ui)
}
