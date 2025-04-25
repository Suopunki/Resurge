lazy val root = (project in file("."))
  .settings(
    name := "Result",
    version := "0.1.0",

    scalaVersion := "3.3.5",

    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.2.19",
      "org.scalatest" %% "scalatest" % "3.2.19" % "test"
    )
  )
