addCommandAlias("buildGame", ";compile;fastOptJS;indigoBuild")
addCommandAlias("runGame", ";compile;fastOptJS;indigoRun")
addCommandAlias("buildGameFull", ";compile;fullOptJS;indigoBuildFull")
addCommandAlias("runGameFull", ";compile;fullOptJS;indigoRunFull")

lazy val tetris =
  (project in file("."))
    .enablePlugins(ScalaJSPlugin, SbtIndigo) // Enable the Scala.js and Indigo plugins
    .settings( // Standard SBT settings
      name := "tetris",
      version := "0.0.1",
      scalaVersion := "3.1.1",
      organization := "in.gav"
    )
    .settings( // Indigo specific settings
      showCursor := true,
      title := "Tetris",
      gameAssetsDirectory := "assets",
      windowStartWidth := 960, // Width of Electron window, used with `indigoRun`.
      windowStartHeight := 540, // Height of Electron window, used with `indigoRun`.
      libraryDependencies ++= Seq(
        "io.indigoengine" %%% "indigo" % "0.12.1",
        "io.indigoengine" %%% "indigo-extras" % "0.12.1",
        "io.indigoengine" %%% "indigo-json-circe" % "0.12.1",
        "org.scalatest" %% "scalatest" % "3.2.12" % Test
      )
    )
