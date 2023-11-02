organization := "edu.berkeley.cs"

version := "1.3-SNAPSHOT"

name := "hardfloat"

scalaVersion := "2.12.12"

// scalacOptions += "-Xsource:2.11"
scalacOptions := Seq("-deprecation", "-Xsource:2.11")
resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

val defaultVersions = Map("chisel3" -> "3.3-SNAPSHOT")

// Provide a managed dependency on chisel if -DchiselVersion="" issupplied on the command line.
// libraryDependencies += "edu.berkeley.cs" %% "chisel-iotesters" % "1.5.4"
libraryDependencies += "edu.berkeley.cs" %% "chiseltest" % "0.3.1"
libraryDependencies += "edu.berkeley.cs" %% "chisel-iotesters" % "1.5.1"
libraryDependencies ++= (Seq("chisel3").map {
  dep: String => "edu.berkeley.cs" %% dep % sys.props.getOrElse(dep + "Version", defaultVersions(dep))
})
// libraryDependencies += "edu.berkeley.cs" %% "chisel-iotesters" % "1.5.1"
// libraryDependencies += "org. scalatest " %% " scalatest " % "3.0.5"% "test"
libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "3.0.5" % "test");
// com.cainc.data.etl.schema.proficiency.ProficiencySchemaITest

// Test / testForkedParallel := true

publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := { x => false }
// Don't add 'scm' elements if we have a git.remoteRepo definition,
//  but since we don't (with the removal of ghpages), add them in below.
pomExtra := <url>http://chisel.eecs.berkeley.edu/</url>
  <licenses>
    <license>
      <name>BSD-style</name>
      <url>http://www.opensource.org/licenses/bsd-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>https://github.com/ucb-bar/berkeley-hardfloat.git</url>
    <connection>scm:git:github.com/ucb-bar/berkeley-hardfloat.git</connection>
  </scm>
  <developers>
    <developer>
      <id>jhauser-ucberkeley</id>
      <name>John Hauser</name>
    </developer>
    <developer>
      <id>aswaterman</id>
      <name>Andrew Waterman</name>
    </developer>
    <developer>
      <id>yunsup</id>
      <name>Yunsup Lee</name>
    </developer>
  </developers>

publishTo := {
  val v = version.value
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) {
    Some("snapshots" at nexus + "content/repositories/snapshots")
  }
  else {
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }
}

// scalaVersion := "2.12.13"

// scalacOptions ++= Seq(
//   "-deprecation",
//   "-feature",
//   "-unchecked",
//   "-Xfatal-warnings",
//   "-Xsource:2.11",
//   "-language:reflectiveCalls",
//   // Enables autoclonetype2
//   "-P:chiselplugin:useBundlePlugin"
// )

// resolvers ++= Seq(
//   Resolver.sonatypeRepo("snapshots"),
//   Resolver.sonatypeRepo("releases")
// )

// // Chisel 3.4
// addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % "3.4.3" cross CrossVersion.full)
// libraryDependencies += "edu.berkeley.cs" %% "chisel-iotesters" % "1.5.3"
// libraryDependencies += "edu.berkeley.cs" %% "chiseltest" % "0.3.3"

// libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"