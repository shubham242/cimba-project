plugins {
    scala
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

tasks{
    shadowJar{
        archiveBaseName.set("scala-service")
        archiveVersion.set("")
        archiveClassifier.set("")
    }
}

dependencies {
    implementation ("org.scala-lang:scala3-library_3:3.3.1")
    implementation ("org.jsoup:jsoup:1.17.2")
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("org.postgresql:postgresql:42.7.3")
    implementation( "org.json4s:json4s-jackson_3:4.1.0-M5")
}
