plugins {
    alias(libs.plugins.gymster.android.library)
    alias(libs.plugins.gymster.compose)
}

dependencies {
    implementation(projects.shared.feature.home)
    implementation(projects.androidApp.ui)
}

android {
    namespace = "com.maruchin.gymster.android.home"
}
