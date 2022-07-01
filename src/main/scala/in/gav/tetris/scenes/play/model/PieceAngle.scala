package in.gav.tetris.scenes.play.model

import indigo.Radians

import scala.util.Random

sealed trait PieceAngle:
  val radians: Radians
  val clockwise: PieceAngle
  val antiClockwise: PieceAngle

object PieceAngle:

  case object `0` extends PieceAngle :
    override val radians: Radians = Radians.zero
    override val clockwise: PieceAngle = PieceAngle.`270`
    override val antiClockwise: PieceAngle = PieceAngle.`90`

  case object `90` extends PieceAngle :
    override val radians: Radians = Radians.PIby2
    override val clockwise: PieceAngle = PieceAngle.`0`
    override val antiClockwise: PieceAngle = PieceAngle.`180`

  case object `180` extends PieceAngle :
    override val radians: Radians = Radians.PI
    override val clockwise: PieceAngle = PieceAngle.`90`
    override val antiClockwise: PieceAngle = PieceAngle.`270`

  case object `270` extends PieceAngle :
    override val radians: Radians = Radians.PIby2
    override val clockwise: PieceAngle = PieceAngle.`180`
    override val antiClockwise: PieceAngle = PieceAngle.`0`
