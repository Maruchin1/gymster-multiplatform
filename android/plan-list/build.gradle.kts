plugins {
    alias(libs.plugins.gymster.android.library)
    alias(libs.plugins.gymster.compose)
}

android {
    namespace = "com.maruchin.gymster.android.planlist"
}

dependencies {
    implementation(projects.shared.feature.planList)
    implementation(projects.shared.core.clock)
    implementation(projects.android.ui)
}
