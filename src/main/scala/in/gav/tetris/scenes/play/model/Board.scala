package in.gav.tetris.scenes.play.model

import cats.Show
import indigo.{IndigoLogger, Outcome, Point, Size}
import in.gav.tetris.events.{BlockPlaced, GameOver}
import in.gav.tetris.scenes.play.model.{BlockState, Piece}

import scala.annotation.tailrec

/**
 * todo: choose Seq type
 * [  ][0][1][2][3][4][5][6][7][8][9]
 * [ 0][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [ 1][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [ 2][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [ 3][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [ 4][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [ 5][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [ 6][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [ 7][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [ 8][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [ 9][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [10][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [11][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [12][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [13][ ][ ][ ][ ][A][ ][ ][ ][ ][ ]
 * [14][ ][ ][ ][A][A][ ][ ][ ][ ][ ]
 * [15][ ][ ][ ][A][ ][ ][ ][ ][ ][ ]
 * [16][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * [17][ ][S][S][ ][ ][ ][ ][ ][S][S]
 * [18][ ][S][S][S][ ][ ][ ][S][S][S]
 * [19][S][S][S][S][S][S][S][S][S][S]
 */

case class Board(grid: Grid, piece: Piece):

  private lazy val activePoints: Seq[Point] = grid.blocks.filter(_.state.isActive).map(_.point)

  // Point positions
  private lazy val downPoints: Seq[Point] = activePoints.map(b => Point(b.x, b.y + 1))
  private lazy val rightPoints: Seq[Point] = activePoints.map(b => Point(b.x + 1, b.y))
  private lazy val leftPoints: Seq[Point] = activePoints.map(b => Point(b.x - 1, b.y))
  private lazy val clockwisePoints: Seq[Point] = piece.toAngle(activePoints, piece.clockwise)
  private lazy val antiClockwisePoints: Seq[Point] = piece.toAngle(activePoints, piece.antiClockwise)

  // Ghost positions
  private lazy val dropDownPoints: Seq[Point] = grid.maxDropPoints(activePoints)
  private lazy val dropRightPoints: Seq[Point] = grid.maxDropPoints(rightPoints)
  private lazy val dropLeftPoints: Seq[Point] = grid.maxDropPoints(leftPoints)
  private lazy val dropClockwisePoints: Seq[Point] = grid.maxDropPoints(clockwisePoints)
  private lazy val dropAntiClockwisePoints: Seq[Point] = grid.maxDropPoints(antiClockwisePoints)

  // Movement booleans
  // todo: allow wall kicks near edges and bottom
  private lazy val canMoveDown: Boolean = grid.canMove(downPoints) && grid.clearBottomWall(downPoints)
  private lazy val canMoveRight: Boolean = grid.canMove(rightPoints) && grid.clearRightWall(rightPoints)
  private lazy val canMoveLeft: Boolean = grid.canMove(leftPoints) && grid.clearLeftWall(leftPoints)
  private lazy val canRotateClockwise: Boolean = grid.canMove(clockwisePoints) && grid.clearAllWall(clockwisePoints)
  private lazy val canRotateAntiClockwise: Boolean = grid.canMove(antiClockwisePoints) && grid.clearAllWall(antiClockwisePoints)

  // Block changes
  private lazy val activeSolid: Grid = grid.changeBlocks(activePoints, piece.solid)
  private lazy val activeEmpty: Grid = grid.changeBlocks(activePoints, BlockState.Empty)
  private lazy val activeDownEmpty: Grid = grid.changeBlocks(activePoints ++ dropDownPoints, BlockState.Empty)

  lazy val tickDown: Outcome[Board] =
    if canMoveDown then
      Outcome(activeEmpty.changeBlocks(downPoints, piece.active))
    else Outcome(activeSolid, List(BlockPlaced))

  lazy val moveDrop: Outcome[Board] =
    if canMoveDown then
      Outcome(activeEmpty.changeBlocks(dropDownPoints, piece.solid), List(BlockPlaced))
    else Outcome(activeSolid, List(BlockPlaced))

  lazy val moveDown: Board =
    if canMoveDown then
      activeEmpty.changeBlocks(downPoints, piece.active)
    else grid

  lazy val moveRight: Board =
    if canMoveRight then
      activeDownEmpty.changeBlocks(dropRightPoints, piece.ghost).changeBlocks(rightPoints, piece.active)
    else grid

  lazy val moveLeft: Board =
    if canMoveLeft then
      activeDownEmpty.changeBlocks(dropLeftPoints, piece.ghost).changeBlocks(leftPoints, piece.active)
    else grid

  lazy val rotateClockwise: Board =
    if canRotateClockwise then
      Board(activeDownEmpty
        .changeBlocks(dropClockwisePoints, piece.clockwise.ghost)
        .changeBlocks(clockwisePoints, piece.clockwise.active), piece.clockwise)
    else grid

  lazy val rotateAntiClockwise: Board =
    if canRotateAntiClockwise then
      Board(activeDownEmpty
        .changeBlocks(dropAntiClockwisePoints, piece.antiClockwise.ghost)
        .changeBlocks(antiClockwisePoints, piece.antiClockwise.active), piece.antiClockwise)
    else grid

  implicit def toBoard(newGrid: Grid): Board = Board(newGrid, piece)

object Board:

  def apply(grid: Grid, shape: PieceShape): Board =
    val piece = Piece(shape)
    val points = piece.points.map(p => Point(p.x + 3, p.y))
    val nextGrid = grid
      .changeBlocks(grid.maxDropPoints(points), piece.ghost)
      .changeBlocks(points, piece.active)
    Board(nextGrid, piece)
