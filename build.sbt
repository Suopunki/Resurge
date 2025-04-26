lazy val root = (project in file("."))
  .settings(
    name := "resurge",
    version := "0.1.0",
    scalaVersion := "3.3.5",

    publishTo := Some(Resolver.file("Local", new File(Path.userHome.absolutePath + "/.ivy2/local"))),
    publishMavenStyle := true,

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"
  )
