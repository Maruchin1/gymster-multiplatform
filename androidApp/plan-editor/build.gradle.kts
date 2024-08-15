plugins {
    alias(libs.plugins.gymster.android.library)
    alias(libs.plugins.gymster.compose)
}

android {
    namespace = "com.maruchin.gymster.android.planeditor"
}

dependencies {
    implementation(projects.shared.feature.planEditor)
    implementation(projects.androidApp.ui)
    implementation(libs.reorderable)
}
