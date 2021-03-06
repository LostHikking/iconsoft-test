plugins {
	java
	id("application")
	id("org.openjfx.javafxplugin") version "0.0.9"
}

repositories {
	mavenCentral()
}

apply(plugin = "org.openjfx.javafxplugin")

javafx {
	version = "14"
	modules("javafx.controls", "javafx.fxml")
}

application {
	mainClass.set("io.github.losthikking.iconsofttest.Main")
}

group = "io.github.losthikking"
version = "1.0-SNAPSHOT"

dependencies {
	implementation("org.slf4j", "slf4j-api", "1.7.30")
	implementation("org.slf4j", "slf4j-simple", "1.7.30")
	testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
}

tasks.test {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
	}
}