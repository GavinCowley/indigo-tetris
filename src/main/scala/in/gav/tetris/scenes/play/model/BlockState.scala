package in.gav.tetris.scenes.play.model

import in.gav.tetris.scenes.play.model

sealed trait BlockState:
  val shape: Option[PieceShape]
  lazy val isActive: Boolean = false
  lazy val isSolid: Boolean = false

object BlockState:
  case object Empty extends BlockState :
    override val shape: Option[PieceShape] = None

  case class Active(shape: Option[PieceShape]) extends BlockState :
    override lazy val isActive: Boolean = true

  case class Ghost(shape: Option[PieceShape]) extends BlockState

  case class Solid(shape: Option[PieceShape]) extends BlockState :
    override lazy val isSolid: Boolean = true
