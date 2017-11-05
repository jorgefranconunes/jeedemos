
lazy val commonSettings = List(
    organization := "com.example",
    scalaVersion := "2.12.3",
    version      := "0.1.0-SNAPSHOT",
    exportJars   := true
)

lazy val projRoot = (project in file ("."))
    .settings(
        commonSettings,
        publish := {},
        publishLocal := {},
        skip in publish := true)
    .aggregate(
        projApi,
        projCli)

lazy val projApi = (project in file("api"))
    .settings(
        commonSettings,
        name := "helloworld-api")

lazy val projCli = (project in file("cli"))
    .settings(
        commonSettings,
        name := "helloworld-cli")
    .dependsOn(projApi)
