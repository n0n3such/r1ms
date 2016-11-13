
name := "onceness"

version := "latest"

lazy val minorVersion = "0.4"

scalaVersion := "2.11.8"

scalacOptions in ThisBuild += "-feature"

lazy val root = project in file(".")

enablePlugins(PlayScala)

enablePlugins(SbtNativePackager)

enablePlugins(DockerPlugin)

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/public/",
  "RoundEights" at "http://maven.spikemark.net/roundeights"
)

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.9.6",
  "org.joda" % "joda-convert" % "1.8.1",
  "net.glxn" % "qrgen" % "1.4",
  "org.apache.directory.studio" % "org.apache.commons.codec" % "1.8",
  "com.typesafe.akka" %% "akka-actor" % "2.3.16",
  "com.typesafe.akka" %% "akka-agent" % "2.3.16",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.12.0-play23",
  "com.roundeights" %% "hasher" % "1.0.0"
)

import com.typesafe.sbt.packager.docker._

doc in Compile <<= target.map(_ / "none")

publishArtifact in (Compile, packageSrc) := false

publishArtifact in (Compile, packageDoc) := false

maintainer in Docker := "n0n3such@onceness.com"

dockerExposedPorts := Seq(8080)

dockerRepository := Some("n0n3such")


