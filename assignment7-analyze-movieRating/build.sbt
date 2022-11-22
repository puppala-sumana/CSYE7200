ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

val scalaTestVersion = "3.2.3"

libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % "test"

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.2.1"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.2.1"

lazy val root = (project in file("."))
  .settings(
    name := "assignment-analyze-movieRating"
  )
