plugins {
    alias(libs.plugins.gymster.android.library)
    alias(libs.plugins.gymster.compose)
}

android {
    namespace = "com.maruchin.multiplatform.gymster.android.trainingplans"
}

dependencies {
    implementation(projects.shared.feature.trainingPlans)
    implementation(libs.reorderable)
}
