pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Book Trails"
include(":app")
include(":feature_auth_module")
include(":ui_module")
include(":feature_reading_module")
include(":feature_bestseller_books_module")
include(":feature_profile_module")
include(":feature_book_management_module")
include(":core_module")
include(":core_network_module")
