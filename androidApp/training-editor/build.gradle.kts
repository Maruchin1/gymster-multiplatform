plugins {
    alias(libs.plugins.gymster.android.library)
    alias(libs.plugins.gymster.compose)
}

android {
    namespace = "com.maruchin.gymster.android.trainingeditor"
}

dependencies {
    implementation(projects.shared.feature.trainingEditor)
    implementation(projects.androidApp.ui)
}
