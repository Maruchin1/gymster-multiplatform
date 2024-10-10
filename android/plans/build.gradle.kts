plugins {
    alias(libs.plugins.gymster.android.library)
    alias(libs.plugins.gymster.compose)
}

android {
    namespace = "com.maruchin.gymster.android.plans"
}

dependencies {
    implementation(projects.shared.feature.plans)
    implementation(projects.shared.core.utils)
    implementation(projects.android.common)
    implementation(libs.reorderable)
}
