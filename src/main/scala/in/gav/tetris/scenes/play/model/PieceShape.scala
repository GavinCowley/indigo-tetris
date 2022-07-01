package in.gav.tetris.scenes.play.model

import indigo.shared.datatypes.RGBA
import indigo.{IndigoLogger, Point, Vector2}

import scala.util.Random

sealed trait PieceShape

object PieceShape:

  case object IShape extends PieceShape

  case object LShape extends PieceShape

  case object JShape extends PieceShape

  case object OShape extends PieceShape

  case object TShape extends PieceShape

  case object SShape extends PieceShape

  case object ZShape extends PieceShape

  private val All = Seq(IShape, LShape, JShape, OShape, TShape, ZShape, SShape)
  private val AllLength = All.length
  private val random = new Random()

  def newRandom: PieceShape =
    val nextInt = random.nextInt(AllLength)
    All.applyOrElse(nextInt, i => if i % 2 == 0 then ZShape else SShape)
