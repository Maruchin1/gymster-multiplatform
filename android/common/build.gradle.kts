plugins {
    alias(libs.plugins.gymster.android.library)
    alias(libs.plugins.gymster.compose)
}

android {
    namespace = "com.maruchin.gymster.android.common"
}

dependencies {
    implementation(libs.compose.ui.fonts)
}
