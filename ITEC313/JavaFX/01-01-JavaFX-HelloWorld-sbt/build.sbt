name := "HelloFX"

version := "0.1.0"

// SBT is Scala based; we still need a Scala version, but we don't write Scala sources
scalaVersion := "2.13.14"

// Pull JavaFX modules we need. Add others (graphics, fxml, media, etc.) if required.
val javafxVersion = "24.0.2"
libraryDependencies ++= Seq(
  "org.openjfx" % "javafx-controls" % javafxVersion,
  "org.openjfx" % "javafx-graphics" % javafxVersion classifier (if (System.getProperty("os.name").toLowerCase.contains("mac")) "mac-aarch64" else "mac")
)

// Make it a pure Java style project
crossPaths := false

// Main class
Compile / run / mainClass := Some("HelloFX")

// Fork a new JVM so module path options apply only to the app
fork := true

// Automatically build module-path from resolved JavaFX jars
javaOptions ++= {
  val cp = (Runtime / fullClasspath).value.map(_.data).filter(f => f.getName.startsWith("javafx") && f.getName.endsWith(".jar"))
  val modulePath = cp.mkString(java.io.File.pathSeparator)
  Seq(
    "--module-path", modulePath,
  "--add-modules", "javafx.controls"
  )
}

// Enable native access for the JavaFX graphics module to silence future restrictive warnings (Java 22+)
javaOptions += "--enable-native-access=javafx.graphics"

// Ensure Java sources are picked up (SBT already uses src/main/java by default)
Compile / unmanagedSourceDirectories += (Compile / sourceDirectory).value / "java"

// Use default classDirectory (target/scala-*/classes) to keep incremental compile stable
