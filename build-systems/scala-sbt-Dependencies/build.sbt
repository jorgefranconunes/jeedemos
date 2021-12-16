
ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version := "0.1.0-SNAPSHOT"


lazy val projRoot = (project in file ("."))
    .aggregate(
        projApi,
        projCli,
        projRelease)
    .settings(
        publish := {},
        publishLocal := {},
        skip in publish := true)


lazy val projApi = (project in file("api"))
    .settings(
        name := "helloworld-api",
        exportJars := true)


lazy val projCli = (project in file("cli"))
    .dependsOn(projApi)
    .settings(
        name := "helloworld-cli",
        mainClass := Some("com.varmateo.Main"),
        libraryDependencies ++= Seq(
            "com.varmateo.yawg" % "yawg-api" % "0.9.8"),
        exportJars := true)

projCli / packageOptions += {
    import java.util.jar.Attributes.Name

    val classpath = (projCli / Compile / dependencyClasspathAsJars).value
    val classpathNames: Seq[String] = classpath
        .map(_.data)
        .filter(_.isFile)
        .map(_.getName)
        .sorted

    Package.ManifestAttributes(
        Name.CLASS_PATH -> classpathNames.mkString(" "))
}


lazy val projRelease = project
    .dependsOn(projCli)

val copyDependencies = taskKey[String]("Copy project dependencies JAR files to release lib directory")
val cleanDependencies = taskKey[String]("Remove JAR file from release lib directory")

projRelease / copyDependencies := copyDependenciesImpl(
    (projRelease / baseDirectory).value,
    (projRelease / dependencyClasspath).in(Runtime).value.map(_.data),
    sLog.value)

projRelease / copyDependencies :=
    ((projRelease / copyDependencies) triggeredBy (projRelease / Compile /sbt.Keys.`package`)).value

projRelease / cleanDependencies := cleanDependenciesImpl((projRelease / baseDirectory).value)

projRelease / cleanDependencies :=
    ((projRelease / cleanDependencies) triggeredBy (projRelease / clean)).value


def copyDependenciesImpl(
    baseDirectory: File,
    dependencyJars: Seq[File],
    log: Logger
): String = {
    import java.nio.file.Files
    import java.nio.file.Path
    import java.nio.file.StandardCopyOption

    val jarFiles: Seq[Path] = dependencyJars.filter(_.isFile).map(_.toPath)
    val targetLibDir: Path = (baseDirectory / "../lib/jars").toPath

    log.info(s"Copying dependencies to: ${targetLibDir}")

    Files.createDirectories(targetLibDir)
    jarFiles.foreach { jarFile =>
        val fileName: Path = jarFile.getFileName
        Files.copy(jarFile, targetLibDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING)
        log.info(s"    ${fileName}")
    }

    targetLibDir.toString
}


def cleanDependenciesImpl(baseDirectory: File): String = {
    import java.nio.file.Files
    import java.nio.file.Path

    val targetLibDir: File = baseDirectory / "../lib/jars"
    val filesToDelete: Seq[Path] = (targetLibDir ** "*.jar").get().map(_.toPath)

    filesToDelete.foreach(Files.delete(_))

    targetLibDir.toString
}
