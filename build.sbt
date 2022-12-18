ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.1"
ThisBuild / scalacOptions += "-Xcheck-macros"

lazy val root = (project in file("."))
  .settings(
    name := "scalabug13955",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest"          % "3.2.14" % Test,
      "org.scalatest" %% "scalatest-flatspec" % "3.2.14" % Test
    )
  )
