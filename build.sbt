name := "wastedTimeCalc"

version := "0.1"

scalaVersion := "2.11.7"

lazy val sparkVersion = "2.4.5"
lazy val akkaVersion = "2.6.0-M2"

//Artifactory access credentials goes here
credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

resolvers ++= Seq(
  "Artifactory" at "https://artifactory.jivox.com/artifactory/sbt_repo/",
  Resolver.mavenLocal
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % {{sparkVersion}},
  "org.apache.spark" %% "spark-sql" % {{sparkVersion}},
  "com.typesafe" % "config" % "1.3.4",
)

libraryDependencies += "com.google.guava" % "guava" % "28.2-jre"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.10.0"
libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.7.2"