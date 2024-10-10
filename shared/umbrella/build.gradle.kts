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
            export(projects.shared.feature.home)
            export(projects.shared.feature.plans)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.shared.feature.home)
            api(projects.shared.feature.plans)
            implementation(projects.shared.data.plans)
            implementation(projects.shared.data.trainings)
            implementation(projects.shared.core.database)
            implementation(projects.shared.core.utils)
            implementation(projects.shared.core.settings)
        }
    }
}

android {
    namespace = "com.maruchin.gymster.umbrella"
}
