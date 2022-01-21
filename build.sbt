//val scala3Version = "3.0.0"
val scala3Version = "3.1.2-RC1-bin-SNAPSHOT"

lazy val root = project
  .in(file("."))
  .dependsOn(plugin)
  .settings(
    name := "scala3-simple",
    version := "0.1.0",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "com.novocode" % "junit-interface" % "0.11" % "test",
      "org.scala-lang" %% "scala3-compiler" % scalaVersion.value % "provided",
      "org.scala-lang" % "scala-reflect" % "2.13.6"
    )
  )

lazy val plugin = project
  .in(file("plugin"))
  .settings(
    name := "dividezero",
    version := "0.0.1",
    organization := "org.scala-lang",
    scalaVersion := scala3Version,

    scalacOptions ++= Seq(
      "-language:implicitConversions"
    ),

    libraryDependencies ++= Seq(
      "org.scala-lang" %% "scala3-compiler" % scalaVersion.value % "provided"
    )
  )