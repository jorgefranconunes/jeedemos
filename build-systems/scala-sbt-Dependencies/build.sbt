
lazy val commonSettings = List(
    organization := "com.example",
    scalaVersion := "2.13.1",
    version      := "0.1.0-SNAPSHOT",
    exportJars   := true
)

lazy val projRoot = (project in file ("."))
    .aggregate(
        projApi,
        projCli)
    .settings(
        commonSettings,
        publish := {},
        publishLocal := {},
        skip in publish := true)

lazy val projApi = (project in file("api"))
    .settings(
        commonSettings,
        name := "helloworld-api")

lazy val projCli = (project in file("cli"))
    .settings(
        commonSettings,
        name := "helloworld-cli",
        libraryDependencies ++= dependencies)
    .dependsOn(projApi)


lazy val dependencies = Seq(
    "com.varmateo.yawg" % "yawg-api" % "0.9.8"
)
