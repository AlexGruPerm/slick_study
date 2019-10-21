name := "SlickStudy"
version := "1.0"

resolvers += Resolver.sonatypeRepo("snapshots")
scalaVersion := "2.13.0"
scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies += "com.datastax.oss" % "java-driver-core" % "4.0.1"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.3.0-alpha4"
libraryDependencies +="com.typesafe" % "config" % "1.3.4"
libraryDependencies +=  "com.typesafe.akka" %% "akka-actor" % "2.5.23"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.2",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
  "org.postgresql" % "postgresql" % "42.2.5"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case "plugin.properties" => MergeStrategy.last
  case "log4j.properties" => MergeStrategy.last
  case "logback.xml" => MergeStrategy.last
  case "resources/logback.xml" => MergeStrategy.last
  case "resources/application.conf" => MergeStrategy.last
  case "application.conf" => MergeStrategy.last
  case PathList("reference.conf") => MergeStrategy.concat
  case x => MergeStrategy.first
}

assemblyJarName in assembly :="SlickStudy.jar"
mainClass in (Compile, packageBin) := Some("SlickStudy.main")
mainClass in (Compile, run) := Some("SlickStudy.main")

