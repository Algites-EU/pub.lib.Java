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

plugins {
    `java-library`
    `maven-publish`
}

// --- Dependency resolution repos (read) ---
allprojects {
    layout.buildDirectory.set(
        rootProject.layout.projectDirectory.dir("run/bld/gradle/${project.name}")
    )
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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

/* --- START OF: common sniplet for repo publishment --- */
fun String.capFirst(): String =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

val locVisibility = providers.gradleProperty("ALGITES_VISIBILITY").orNull ?: "pub"
val locDirection  = providers.gradleProperty("ALGITES_DIRECTION").orNull ?: "upload"

val locRepoUrl  = providers.gradleProperty("ALGITES_REPO_URL").orNull
val locRepoUser = providers.gradleProperty("ALGITES_REPO_USER").orNull
val locRepoPass = providers.gradleProperty("ALGITES_REPO_PASS").orNull

val locHasRemoteRepo = (locRepoUrl != null && locRepoUser != null && locRepoPass != null)

/* Internal Gradle repo name (stable, camelCase) */
val locRepoName = buildString {
    append("algites")
    append(locVisibility.capFirst())
    append(locDirection.capFirst())
}

publishing {
    repositories {

        if (locRepoUrl != null && locRepoUser != null && locRepoPass != null) {
            maven {
                name = locRepoName
                url = uri(locRepoUrl)
                credentials {
                    username = locRepoUser
                    password = locRepoPass
                }
            }
        } else {
            logger.lifecycle("Remote repo disabled (ALGITES_REPO_* not set). Repo name would be '$locRepoName'.")
        }
    }
}

val locIsCi = providers.environmentVariable("CI").orNull == "true"
if (locIsCi && !locHasRemoteRepo) {
    throw GradleException("CI build requires ALGITES_REPO_* for publishing.")
}
tasks.named("publish") {
    if (!locIsCi && !locHasRemoteRepo) {
        dependsOn("publishToMavenLocal")
    }
}
/* --- END OF: common sniplet for repo publishment --- */
