import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class RealmConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        apply(plugin = "io.realm.kotlin")
        configure<KotlinMultiplatformExtension> {
            with(sourceSets) {
                commonMain.dependencies {
                    implementation(libs.findLibrary("realm-base").get())
                }
            }
        }
    }
}
