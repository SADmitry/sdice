val scala3Version = "3.4.2"

publishMavenStyle := true
versionScheme := Some("semver-spec")

publishTo := {
  val ghOrg = "SADmitry"
  val repo = "sdice"
  val packages = s"https://maven.pkg.github.com/$ghOrg/$repo"
  Some("GitHub Package Registry" at packages)
}

developers := List(
  Developer(
    id    = "SADmitry",
    name  = "Dmitry",
    email = "d.a.solovyov@gmail.com",
    url   = url("https://github.com/SADmitry")
  )
)

credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  "SADmitry",
  sys.env.get("TOKEN")
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "sdice",
    version := "0.6.0",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )
