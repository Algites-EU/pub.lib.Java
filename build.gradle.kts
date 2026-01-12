//import org.gradle.api.publish.PublishingExtension
//
//plugins {
//    // root je jen orchestrátor; publication bude až v subprojektech
//    `maven-publish`
//}
//
//import java.util.Properties
//
//        allprojects {
//
//            val repoProps = Properties().apply {
//                loader(rootProject.file("algites-source-repository.properties").inputStream())
//            }
//
//            val lane = repoProps.getProperty("algites.repository.lane")
//            val revision = repoProps.getProperty("algites.repository.lane.revision")
//            val suffix = repoProps.getProperty("algites.repository.lane.revision.suffix")
//
//            group = "eu.algites.tool.build"
//            version = "$lane.$revision$suffix"
//        }
//
//// Put Gradle build outputs under run/
//layout.buildDirectory.set(file("run/bld"))
//subprojects {
//    layout.buildDirectory.set(file("${project.projectDir}/run/bld"))
//}
//
//
//// --- GitHub Packages target repo (dynamic in CI) ---
//val ghRepo: String = (
//        System.getenv("ALGITES_GITHUB_REPOSITORY")
//            ?: System.getenv("GITHUB_REPOSITORY")
//            ?: "Algites-EU/pub.tool.Java" // local fallback
//        ).trim()
//
//val ghPackagesUrl = uri("https://maven.pkg.github.com/$ghRepo")
//
//fun ghUser(): String? =
//    (findProperty("gpr.user") as String?)?.takeIf { it.isNotBlank() }
//        ?: System.getenv("GITHUB_ACTOR")?.takeIf { it.isNotBlank() }
//
//fun ghToken(): String? =
//    (findProperty("gpr.token") as String?)?.takeIf { it.isNotBlank() }
//        ?: System.getenv("GITHUB_TOKEN")?.takeIf { it.isNotBlank() }

import java.util.Properties

//fun computeAlgitesRepositoryVersion(): String {
//    val props = Properties()
//    file("algites-source-repository.properties").inputStream().use {
//        props.load(it)
//    }
//
//    val lane = props.getProperty("algites.repository.lane")
//    val revision = props.getProperty("algites.repository.lane.revision")
//    val suffix = props.getProperty("algites.repository.lane.revision.suffix") ?: ""
//
//    return "$lane.$revision$suffix"
//}
//
//version=computeAlgitesRepositoryVersion()

// --- Dependency resolution repos (read) ---
allprojects {
    layout.buildDirectory.set(
        rootProject.layout.projectDirectory.dir("run/bld/gradle/${project.name}")
    )

//    // --- Publishing repo (write) for projects that apply maven-publish ---
//    plugins.withId("maven-publish") {
//        extensions.configure<PublishingExtension>("publishing") {
//            repositories {
//                maven {
//                    name = "GitHubPackages"
//                    url = ghPackagesUrl
//
//                    val u = ghUser()
//                    val t = ghToken()
//                    if (u != null && t != null) {
//                        credentials {
//                            username = u
//                            password = t
//                        }
//                    }
//                }
//            }
//        }
//    }
}

// Simple sanity task
tasks.register("ciHelp") {
    doLast { println("Algites root Gradle build detected.") }
}

allprojects {
    tasks.withType<Test>().configureEach {
        useTestNG()
    }
}
