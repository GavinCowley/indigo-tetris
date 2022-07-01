package in.gav.tetris.scenes.play.model;

import indigo.Point

case class Block(point: Point, state: BlockState):
  lazy val isOffGrid: Boolean = point.y <= 0 && state.isSolid

object Block:
  def apply(x: Int, y: Int): Block = Block(Point(x, y), BlockState.Empty)
