import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "pl.nadwey.nadblocks" // TODO: Change this to your group
version = "1.0.0-beta.2" // TODO: Change this to your addon version

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.paperweight)
    alias(libs.plugins.nova)
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.xenondevs.xyz/releases")
}

dependencies {
    paperweight.paperDevBundle(libs.versions.paper)
    implementation(libs.nova)
}

addon {
    id.set(project.name)
    name.set(project.name.uppercase())
    version.set(project.version.toString())
    novaVersion.set(libs.versions.nova)
    main.set("pl.nadwey.nadblocks.NadBlocks") // TODO: Change this to your main class
    authors.add("Nadwey") // TODO: Set your list of authors
}

tasks {
    register<Copy>("addonJar") {
        group = "build"

        dependsOn("jar")

        from(File(layout.buildDirectory.asFile.get(), "libs/${project.name}-${project.version}.jar"))
        
        into((project.findProperty("outDir") as? String)?.let(::File) ?: project.buildDir)
        rename { "${addonMetadata.get().addonName.get()}-${project.version}.jar" }
    }
    
    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget to JvmTarget.JVM_21
        }
    }
}