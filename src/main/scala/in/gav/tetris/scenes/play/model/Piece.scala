package in.gav.tetris.scenes.play.model

import in.gav.tetris.scenes.play.model.{BlockPositionIdentifiers, PieceAngle, PieceShape}
import indigo.Point

import scala.util.Random

sealed abstract class Piece(val shape: PieceShape, val angle: PieceAngle):
  val A: Point
  val B: Point
  val C: Point
  val D: Point

  lazy val points = Seq(A, B, C, D)

  lazy val clockwise: Piece = Piece(shape, angle.clockwise)
  lazy val antiClockwise: Piece = Piece(shape, angle.antiClockwise)

  lazy val active: BlockState = BlockState.Active(Some(shape))
  lazy val ghost: BlockState = BlockState.Ghost(Some(shape))
  lazy val solid: BlockState = BlockState.Solid(Some(shape))

  final def currentZero(points: Seq[Point]): Point = points.minBy(p => (p.x, p.y)) - A

  final def toAngle(current: Seq[Point], next: Piece): Seq[Point] =
    val zero = currentZero(current)
    next.points.map(_ + zero)

object Piece:
  // todo: spawn at top, in correct angle

  /**
   * |       0      |       90     |      180     |      270     |
   * | [ ][A][ ][ ] | [ ][ ][ ][ ] | [ ][ ][A][ ] | [ ][ ][ ][ ] |
   * | [ ][B][ ][ ] | [ ][ ][ ][ ] | [ ][ ][B][ ] | [A][B][C][D] |
   * | [ ][C][ ][ ] | [A][B][C][D] | [ ][ ][C][ ] | [ ][ ][ ][ ] |
   * | [ ][D][ ][ ] | [ ][ ][ ][ ] | [ ][ ][D][ ] | [ ][ ][ ][ ] |
   */
  case object IShapeZero extends Piece(PieceShape.IShape, PieceAngle.`0`) :
    override val A: Point = BlockPositionIdentifiers.BA
    override val B: Point = BlockPositionIdentifiers.BB
    override val C: Point = BlockPositionIdentifiers.BC
    override val D: Point = BlockPositionIdentifiers.BD

  case object IShapeNinety extends Piece(PieceShape.IShape, PieceAngle.`90`) :
    override val A: Point = BlockPositionIdentifiers.AC
    override val B: Point = BlockPositionIdentifiers.BC
    override val C: Point = BlockPositionIdentifiers.CC
    override val D: Point = BlockPositionIdentifiers.DC

  case object IShapeOneEighty extends Piece(PieceShape.IShape, PieceAngle.`180`) :
    override val A: Point = BlockPositionIdentifiers.CA
    override val B: Point = BlockPositionIdentifiers.CB
    override val C: Point = BlockPositionIdentifiers.CC
    override val D: Point = BlockPositionIdentifiers.CD

  case object IShapeTwoSeventy extends Piece(PieceShape.IShape, PieceAngle.`270`) :
    override val A: Point = BlockPositionIdentifiers.AB
    override val B: Point = BlockPositionIdentifiers.BB
    override val C: Point = BlockPositionIdentifiers.CB
    override val D: Point = BlockPositionIdentifiers.DB

  /**
   * |       0      |       90     |      180     |      270     |
   * | [ ][A][ ][ ] | [ ][ ][C][ ] | [A][B][ ][ ] | [ ][ ][ ][ ] |
   * | [ ][B][ ][ ] | [A][B][D][ ] | [ ][C][ ][ ] | [A][C][D][ ] |
   * | [ ][C][D][ ] | [ ][ ][ ][ ] | [ ][D][ ][ ] | [B][ ][ ][ ] |
   * | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] |
   */
  case object LShapeZero extends Piece(PieceShape.LShape, PieceAngle.`0`) :
    override val A: Point = BlockPositionIdentifiers.BA
    override val B: Point = BlockPositionIdentifiers.BB
    override val C: Point = BlockPositionIdentifiers.BC
    override val D: Point = BlockPositionIdentifiers.CC

  case object LShapeNinety extends Piece(PieceShape.LShape, PieceAngle.`90`) :
    override val A: Point = BlockPositionIdentifiers.AB
    override val B: Point = BlockPositionIdentifiers.BB
    override val C: Point = BlockPositionIdentifiers.CA
    override val D: Point = BlockPositionIdentifiers.CB

  case object LShapeOneEighty extends Piece(PieceShape.LShape, PieceAngle.`180`) :
    override val A: Point = BlockPositionIdentifiers.AA
    override val B: Point = BlockPositionIdentifiers.BA
    override val C: Point = BlockPositionIdentifiers.BB
    override val D: Point = BlockPositionIdentifiers.BC

  case object LShapeTwoSeventy extends Piece(PieceShape.LShape, PieceAngle.`270`) :
    override val A: Point = BlockPositionIdentifiers.AB
    override val B: Point = BlockPositionIdentifiers.AC
    override val C: Point = BlockPositionIdentifiers.BB
    override val D: Point = BlockPositionIdentifiers.CB

  /**
   * |       0      |       90     |      180     |      270     |
   * | [ ][B][ ][ ] | [ ][ ][ ][ ] | [ ][A][D][ ] | [A][ ][ ][ ] |
   * | [ ][C][ ][ ] | [A][B][C][ ] | [ ][B][ ][ ] | [B][C][D][ ] |
   * | [A][D][ ][ ] | [ ][ ][D][ ] | [ ][C][ ][ ] | [ ][ ][ ][ ] |
   * | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] |
   */
  case object JShapeZero extends Piece(PieceShape.JShape, PieceAngle.`0`) :
    override val A: Point = BlockPositionIdentifiers.AC
    override val B: Point = BlockPositionIdentifiers.BA
    override val C: Point = BlockPositionIdentifiers.BB
    override val D: Point = BlockPositionIdentifiers.BC

  case object JShapeNinety extends Piece(PieceShape.JShape, PieceAngle.`90`) :
    override val A: Point = BlockPositionIdentifiers.AB
    override val B: Point = BlockPositionIdentifiers.BB
    override val C: Point = BlockPositionIdentifiers.CB
    override val D: Point = BlockPositionIdentifiers.CC

  case object JShapeOneEighty extends Piece(PieceShape.JShape, PieceAngle.`180`) :
    override val A: Point = BlockPositionIdentifiers.BA
    override val B: Point = BlockPositionIdentifiers.BB
    override val C: Point = BlockPositionIdentifiers.BC
    override val D: Point = BlockPositionIdentifiers.CA

  case object JShapeTwoSeventy extends Piece(PieceShape.JShape, PieceAngle.`270`) :
    override val A: Point = BlockPositionIdentifiers.AA
    override val B: Point = BlockPositionIdentifiers.AB
    override val C: Point = BlockPositionIdentifiers.BB
    override val D: Point = BlockPositionIdentifiers.CB

  /**
   * |       0      |       90     |      180     |      270     |
   * | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] |
   * | [ ][A][C][ ] | [ ][A][C][ ] | [ ][A][C][ ] | [ ][A][C][ ] |
   * | [ ][B][D][ ] | [ ][B][D][ ] | [ ][B][D][ ] | [ ][B][D][ ] |
   * | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] |
   */
  case object OShapeZero extends Piece(PieceShape.OShape, PieceAngle.`0`) :
    override val A: Point = BlockPositionIdentifiers.BB
    override val B: Point = BlockPositionIdentifiers.BC
    override val C: Point = BlockPositionIdentifiers.CB
    override val D: Point = BlockPositionIdentifiers.CC

  case object OShapeNinety extends Piece(PieceShape.OShape, PieceAngle.`90`) :
    override val A: Point = BlockPositionIdentifiers.BB
    override val B: Point = BlockPositionIdentifiers.BC
    override val C: Point = BlockPositionIdentifiers.CB
    override val D: Point = BlockPositionIdentifiers.CC

  case object OShapeOneEighty extends Piece(PieceShape.OShape, PieceAngle.`180`) :
    override val A: Point = BlockPositionIdentifiers.BB
    override val B: Point = BlockPositionIdentifiers.BC
    override val C: Point = BlockPositionIdentifiers.CB
    override val D: Point = BlockPositionIdentifiers.CC

  case object OShapeTwoSeventy extends Piece(PieceShape.OShape, PieceAngle.`270`) :
    override val A: Point = BlockPositionIdentifiers.BB
    override val B: Point = BlockPositionIdentifiers.BC
    override val C: Point = BlockPositionIdentifiers.CB
    override val D: Point = BlockPositionIdentifiers.CC

  /**
   * |       0      |       90     |      180     |      270     |
   * | [ ][ ][ ][ ] | [ ][A][ ][ ] | [ ][B][ ][ ] | [ ][B][ ][ ] |
   * | [A][B][D][ ] | [ ][B][D][ ] | [A][C][D][ ] | [A][C][ ][ ] |
   * | [ ][C][ ][ ] | [ ][C][ ][ ] | [ ][ ][ ][ ] | [ ][D][ ][ ] |
   * | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] |
   */
  case object TShapeZero extends Piece(PieceShape.TShape, PieceAngle.`0`) :
    override val A: Point = BlockPositionIdentifiers.AB
    override val B: Point = BlockPositionIdentifiers.BB
    override val C: Point = BlockPositionIdentifiers.BC
    override val D: Point = BlockPositionIdentifiers.CB

  case object TShapeNinety extends Piece(PieceShape.TShape, PieceAngle.`90`) :
    override val A: Point = BlockPositionIdentifiers.BA
    override val B: Point = BlockPositionIdentifiers.BB
    override val C: Point = BlockPositionIdentifiers.BC
    override val D: Point = BlockPositionIdentifiers.CB

  case object TShapeOneEighty extends Piece(PieceShape.TShape, PieceAngle.`180`) :
    override val A: Point = BlockPositionIdentifiers.AB
    override val B: Point = BlockPositionIdentifiers.BA
    override val C: Point = BlockPositionIdentifiers.BB
    override val D: Point = BlockPositionIdentifiers.CB

  case object TShapeTwoSeventy extends Piece(PieceShape.TShape, PieceAngle.`270`) :
    override val A: Point = BlockPositionIdentifiers.AB
    override val B: Point = BlockPositionIdentifiers.BA
    override val C: Point = BlockPositionIdentifiers.BB
    override val D: Point = BlockPositionIdentifiers.BC

  /**
   * |       0      |       90     |      180     |      270     |
   * | [ ][ ][ ][ ] | [ ][A][ ][ ] | [ ][B][D][ ] | [A][ ][ ][ ] |
   * | [ ][B][D][ ] | [ ][B][C][ ] | [A][C][ ][ ] | [B][C][ ][ ] |
   * | [A][C][ ][ ] | [ ][ ][D][ ] | [ ][ ][ ][ ] | [ ][D][ ][ ] |
   * | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] |
   */
  case object RShapeZero extends Piece(PieceShape.SShape, PieceAngle.`0`) :
    override val A: Point = BlockPositionIdentifiers.AC
    override val B: Point = BlockPositionIdentifiers.BB
    override val C: Point = BlockPositionIdentifiers.BC
    override val D: Point = BlockPositionIdentifiers.CB

  case object RShapeNinety extends Piece(PieceShape.SShape, PieceAngle.`90`) :
    override val A: Point = BlockPositionIdentifiers.BA
    override val B: Point = BlockPositionIdentifiers.BB
    override val C: Point = BlockPositionIdentifiers.CB
    override val D: Point = BlockPositionIdentifiers.CC

  case object RShapeOneEighty extends Piece(PieceShape.SShape, PieceAngle.`180`) :
    override val A: Point = BlockPositionIdentifiers.AB
    override val B: Point = BlockPositionIdentifiers.BA
    override val C: Point = BlockPositionIdentifiers.BB
    override val D: Point = BlockPositionIdentifiers.CA

  case object RShapeTwoSeventy extends Piece(PieceShape.SShape, PieceAngle.`270`) :
    override val A: Point = BlockPositionIdentifiers.AA
    override val B: Point = BlockPositionIdentifiers.AB
    override val C: Point = BlockPositionIdentifiers.BB
    override val D: Point = BlockPositionIdentifiers.BC

  /**
   * |       0      |       90     |      180     |      270     |
   * | [ ][ ][ ][ ] | [ ][ ][C][ ] | [A][B][ ][ ] | [ ][C][ ][ ] |
   * | [A][B][ ][ ] | [ ][A][D][ ] | [ ][C][D][ ] | [A][D][ ][ ] |
   * | [ ][C][D][ ] | [ ][B][ ][ ] | [ ][ ][ ][ ] | [B][ ][ ][ ] |
   * | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] | [ ][ ][ ][ ] |
   */
  case object ZShapeZero extends Piece(PieceShape.ZShape, PieceAngle.`0`) :
    override val A: Point = BlockPositionIdentifiers.AB
    override val B: Point = BlockPositionIdentifiers.BB
    override val C: Point = BlockPositionIdentifiers.BC
    override val D: Point = BlockPositionIdentifiers.CC

  case object ZShapeNinety extends Piece(PieceShape.ZShape, PieceAngle.`90`) :
    override val A: Point = BlockPositionIdentifiers.BB
    override val B: Point = BlockPositionIdentifiers.BC
    override val C: Point = BlockPositionIdentifiers.CA
    override val D: Point = BlockPositionIdentifiers.CB

  case object ZShapeOneEighty extends Piece(PieceShape.ZShape, PieceAngle.`180`) :
    override val A: Point = BlockPositionIdentifiers.AA
    override val B: Point = BlockPositionIdentifiers.BA
    override val C: Point = BlockPositionIdentifiers.BB
    override val D: Point = BlockPositionIdentifiers.CB

  case object ZShapeTwoSeventy extends Piece(PieceShape.ZShape, PieceAngle.`270`) :
    override val A: Point = BlockPositionIdentifiers.AB
    override val B: Point = BlockPositionIdentifiers.AC
    override val C: Point = BlockPositionIdentifiers.BA
    override val D: Point = BlockPositionIdentifiers.BB


  final val All: Array[Piece] = Array(
    IShapeZero,
    IShapeNinety,
    IShapeOneEighty,
    IShapeTwoSeventy,
    LShapeZero,
    LShapeNinety,
    LShapeOneEighty,
    LShapeTwoSeventy,
    JShapeZero,
    JShapeNinety,
    JShapeOneEighty,
    JShapeTwoSeventy,
    OShapeZero,
    OShapeNinety,
    OShapeOneEighty,
    OShapeTwoSeventy,
    TShapeZero,
    TShapeNinety,
    TShapeOneEighty,
    TShapeTwoSeventy,
    RShapeZero,
    RShapeNinety,
    RShapeOneEighty,
    RShapeTwoSeventy,
    ZShapeZero,
    ZShapeNinety,
    ZShapeOneEighty,
    ZShapeTwoSeventy
  )

  final def apply(shape: PieceShape): Piece = apply(shape, PieceAngle.`0`)

  final def apply(shape: PieceShape, angle: PieceAngle): Piece =
    All.find(p => p.shape == shape && p.angle == angle).getOrElse(OShapeZero)
