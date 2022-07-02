# Tetris on Indigo

This is a very basic tetris game made with [Indigo](https://indigoengine.io) - the Scala game engine.

Not a very clean project, as I used this to learn some basic game engine mechanics as well some Scala 3.

## Contributing

Feel free to clone, or open pull requests. Some things I didn't get around to:

+ Finish loading screen
+ Add a MenuScene (Some basics exist)
+ Add highscore screen
+ Complete the GameOver event
+ Add Wall/Floor kicks
+ Spawn pieces in normal tetris orientation and position
+ Abide by [tetris rules](https://tetris.fandom.com/wiki/Tetris_Guideline)
+ Tests - bit complex with Scala JS

## Run

`sbt clean fastOptJS indigoRun`

Or follow Indigo's instructions

