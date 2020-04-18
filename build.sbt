lazy val core = (project in file("core")).settings(
  organization := "tf.bug",
  name := "graphmonad",
  version := "0.1.0",
  scalaVersion := "2.13.1",
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % "2.1.1",
  ),
  addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full),
)
