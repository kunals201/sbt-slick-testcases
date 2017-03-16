name := """sliickdemo"""

version := "1.0"

scalaVersion := "2.11.8"


libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.0",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
  "org.postgresql" % "postgresql" % "9.4.1212",
  "mysql" % "mysql-connector-java" % "6.0.5",
"org.scalatest" %% "scalatest" % "3.0.1" % "test"

)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

libraryDependencies += "org.scoverage" % "scalac-scoverage-plugin_2.11" % "1.1.1"

libraryDependencies += "com.h2database" % "h2" % "1.4.193"
