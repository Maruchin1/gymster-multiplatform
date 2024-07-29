plugins {
    alias(libs.plugins.gymster.multiplatform)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
            export(projects.shared.feature.plans)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.shared.feature.plans)
            implementation(projects.shared.data.plans)
            implementation(projects.shared.core.di)
            implementation(projects.shared.core.database)
        }
    }
}

android {
    namespace = "com.maruchin.multiplatform.gymster"
}
