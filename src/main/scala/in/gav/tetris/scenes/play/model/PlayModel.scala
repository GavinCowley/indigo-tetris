package in.gav.tetris.scenes.play.model

import Grid.*
import indigo.*
import in.gav.tetris.events.{BlockPlaced, GameOver}
import in.gav.tetris.model.StartUpData

import scala.collection.immutable.Queue

final case class PlayModel(board: Board, queue: PieceQueue, timeToDown: Seconds, score: Score):

  def update(context: FrameContext[StartUpData], event: GlobalEvent): Outcome[PlayModel] = event match
    case FrameTick if timeToDown <= context.delta =>
      board.tickDown.map(copy(_, timeToDown = Seconds(1)))
    case FrameTick =>
      Outcome(copy(timeToDown = timeToDown - context.delta))
    case KeyboardEvent.KeyDown(Key.SPACE) =>
      board.moveDrop.map(copy(_))
    case KeyboardEvent.KeyDown(Key.DOWN_ARROW) =>
      Outcome(copy(board.moveDown))
    case KeyboardEvent.KeyDown(Key.UP_ARROW) =>
      Outcome(copy(board.rotateClockwise))
    case KeyboardEvent.KeyDown(Key.LEFT_ARROW) =>
      Outcome(copy(board.moveLeft))
    case KeyboardEvent.KeyDown(Key.RIGHT_ARROW) =>
      Outcome(copy(board.moveRight))
    case BlockPlaced =>
      val (nextShape, nextQueue) = queue.dequeue
      val (nextGrid, rows) = board.grid.evaluate
      val nextScore = score.add(rows, nextGrid.isEmpty)
      val nextBoard = if nextGrid.hasFailed then
      // todo: GameOver
        Board(nextGrid, Piece(nextShape))
      else Board(nextGrid, nextShape)
      Outcome(PlayModel(nextBoard, nextQueue, timeToDown, nextScore))
    case _ =>
      Outcome(this)

object PlayModel:

  private def spawner: PieceShape = PieceShape.newRandom

  val Init: PlayModel = PlayModel(
    queue = PieceQueue.init(spawner),
    board = Board(Grid.Init, spawner),
    timeToDown = Seconds(1),
    score = Score.Init
  )
