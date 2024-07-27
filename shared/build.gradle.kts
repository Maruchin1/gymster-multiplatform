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
            export(projects.shared.feature.trainingPlans)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.shared.feature.trainingPlans)
            implementation(projects.shared.data.trainingPlans)
            implementation(projects.shared.core.di)
            implementation(projects.shared.core.database)
        }
    }
}

android {
    namespace = "com.maruchin.multiplatform.gymster"
}
