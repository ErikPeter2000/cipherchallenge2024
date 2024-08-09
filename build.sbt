import Dependencies._

ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "CipherChallenge2024",
    libraryDependencies += munit % Test,
    libraryDependencies += "org.scala-lang.modules" %% "scala-collection-contrib" % "0.2.2",
    libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
