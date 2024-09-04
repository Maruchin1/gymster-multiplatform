plugins {
    alias(libs.plugins.gymster.android.library)
    alias(libs.plugins.gymster.compose)
}

android {
    namespace = "com.maruchin.gymster.android.trainingblockdetails"
}

dependencies {
    implementation(projects.shared.feature.trainingBlockDetails)
    implementation(projects.android.ui)
}
