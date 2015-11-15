name := """chatroomz"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.13",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.5.2",
  "commons-io" % "commons-io" % "2.4",
  "com.typesafe.slick" %% "slick" % "3.1.0",
  "com.typesafe.slick" % "slick-hikaricp_2.11" % "3.1.0",
  "mysql" % "mysql-connector-java" % "5.1.35",
  "com.ibatis" % "ibatis2-common" % "2.1.7.597",
  "org.scalatest" % "scalatest_2.11" % "2.2.5"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
