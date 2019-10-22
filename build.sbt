name := "SlickStudy"

version := "1.0"

scalaVersion := "2.12.8"

lazy val Versions = new {
  val phantom = "2.42.0"
  val slick = "3.3.2"
}

//scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.typesafeRepo("releases"),
  Resolver.bintrayRepo("websudos", "oss-releases")
)

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.3.0-alpha4",
  "com.typesafe.slick" %% "slick" % Versions.slick,
  "com.typesafe.slick" %% "slick-hikaricp" % Versions.slick,
  "org.postgresql" % "postgresql" % "42.2.5",
  "com.outworkers"  %%  "phantom-dsl" % Versions.phantom
)

/*
assemblyJarName in assembly :="SlickStudy.jar"
mainClass in (Compile, packageBin) := Some("pkg.SlickStudy")
*/
mainClass in (Compile, run) := Some("pkg.SlickStudy")

