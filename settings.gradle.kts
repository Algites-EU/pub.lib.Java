pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven {
            name = "algites-public-releases"
            url = uri("https://repo1.maven.org/maven2")
            mavenContent {
                releasesOnly()
            }
        }
        maven {
            name = "algites-public-snapshots"
            url = uri("https://dl.cloudsmith.io/public/algites/maven-snapshots-pub/")
            mavenContent {
                snapshotsOnly()
            }
        }
    }
}

val locIsCi: Boolean =
    providers.gradleProperty("CI")
        .orElse(providers.environmentVariable("CI"))
        .map { it.equals("true", ignoreCase = true) }
        .orElse(false)
        .get()

dependencyResolutionManagement {
    repositories {
        if (!locIsCi) {
            mavenLocal()
        }
        mavenCentral()
        maven {
            name = "algites-public-releases"
            url = uri("https://repo1.maven.org/maven2")
            mavenContent {
                releasesOnly()
            }
        }
        maven {
            name = "algites-public-snapshots"
            url = uri("https://dl.cloudsmith.io/public/algites/maven-snapshots-pub/maven/")
            mavenContent {
                snapshotsOnly()
            }
        }
    }
}

rootProject.name = "pub.lib.Java"
include(":util:common")
include(":util:version")
