/* --- START OF: common sniplet for build.gradle.kts in repo root --- */
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

val locIsCi = providers.environmentVariable("CI").orNull == "true"
if (locIsCi && !locHasRemoteRepo) {
    throw GradleException("CI build requires ALGITES_REPO_* for publishing.")
}


subprojects {
    /* 1) If project uses maven-publish, configure remote repo */
    plugins.withId("maven-publish") {
        extensions.configure<PublishingExtension>("publishing") {
            repositories {
                if (locHasRemoteRepo) {
                    maven {
                        name = locRepoName
                        url = uri(locRepoUrl!!)
                        credentials {
                            username = locRepoUser!!
                            password = locRepoPass!!
                        }
                    }
                }
            }
        }
    }
    /* 2) Local fallback: if NOT CI and no remote repo, publish => publishToMavenLocal */
    tasks.matching { it.name == "publish" }.configureEach {
        if (!locIsCi && !locHasRemoteRepo) {
            dependsOn("publishToMavenLocal")
        }
    }
}

/* Disable publishing tasks in root completely */
if (project == rootProject) {
    tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
    tasks.matching { it.name == "publish" || it.name == "publishToMavenLocal" }.configureEach { enabled = false }
}

/* --- END OF: common sniplet for build.gradle.kts in repo root --- */
