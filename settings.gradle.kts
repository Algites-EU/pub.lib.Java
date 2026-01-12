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

dependencyResolutionManagement {
    repositories {
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

rootProject.name = "pub.lib.Java"
//include(":common")
