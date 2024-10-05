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
            export(projects.shared.feature.trainings)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.shared.feature.home)
            api(projects.shared.feature.plans)
            api(projects.shared.feature.trainings)
            implementation(projects.shared.data.plans)
            implementation(projects.shared.data.trainings)
            implementation(projects.shared.core.database)
            implementation(projects.shared.core.database2)
            implementation(projects.shared.core.clock)
            implementation(projects.shared.core.coroutines)
            implementation(projects.shared.core.settings)
        }
    }
}

android {
    namespace = "com.maruchin.gymster.dupa"
}
