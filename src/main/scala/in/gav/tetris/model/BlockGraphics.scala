package in.gav.tetris.model

import in.gav.tetris.scenes.play.model.{Piece, PieceShape}
import indigo.*

/**
 * -1|1|3|5|7|9|11|13|15|17|19|21
 * |B|B|B|B|B|B|B|B|B|B|B|B| -1
 * |B|0|0|0|0|0|0|0|0|0|0|B|0 1|
 * |B|0|1|1|1|1|1|1|1|1|1|B|1 3|
 * |B|0|1|2|2|2|2|2|2|2|2|B|2 5|
 * |B|0|1|2|3|3|3|3|3|3|3|B|3 7|
 * |B|0|1|2|3|4|4|4|4|4|4|B|4 9|
 * |B|0|1|2|3|4|5|5|5|5|5|B|5 11|
 * |B|0|1|2|3|4|5|6|6|6|6|B|6 13|
 * |B|0|1|2|3|4|5|6|7|7|7|B|7 15|
 * |B|0|1|2|3|4|5|6|7|8|8|B|8 17|
 * |B|0|1|2|3|4|5|6|7|8|9|B|9  19|
 * |B|0|1|2|3|4|5|6|7|8|9|B|10 21|
 * |B|0|1|2|3|4|5|6|7|8|9|B|11 23|
 * |B|0|1|2|3|4|5|6|7|8|9|B|12 25|
 * |B|0|1|2|3|4|5|6|7|8|9|B|13 27|
 * |B|0|1|2|3|4|5|6|7|8|9|B|14 29|
 * |B|0|1|2|3|4|5|6|7|8|9|B|15 31|
 * |B|0|1|2|3|4|5|6|7|8|9|B|16 33|
 * |B|0|1|2|3|4|5|6|7|8|9|B|17 35|
 * |B|0|1|2|3|4|5|6|7|8|9|B|18 37|
 * |B|0|1|2|3|4|5|6|7|8|9|B|19 39|
 * |B|B|B|B|B|B|B|B|B|B|B|B|   41|
 *
 * [   ][UUL] [UUR][   ]
 * [ULL][UL ].[UR ][URR]
 * [DDL][DL ] [DR ][DRR]
 * [   ][DDL] [DDR][   ]
 */
final case class BlockGraphics(graphic: Graphic[Material.ImageEffects]):

  lazy val size: Size = graphic.crop.halfSize
  lazy val width: Int = (size.width * graphic.scale.x).toInt
  lazy val height: Int = (size.height * graphic.scale.y).toInt

  lazy val boardEdge: List[Graphic[Material.ImageEffects]] = List(
    (-1 to 21 by 2).map(i => graphic.moveBy(i * width, -height)),
    (-1 to 21 by 2).map(i => graphic.moveBy(i * width, 41 * height)),
    (-1 to 41 by 2).map(i => graphic.moveBy(-width, i * height)),
    (-1 to 41 by 2).map(i => graphic.moveBy(21 * width, i * height))
  ).flatten.map(_.withMaterial(graphic.material.withTint(RGBA.SlateGray)))

  lazy val queueEdge: List[Graphic[Material.ImageEffects]] = List(
    (27 to 37 by 2).map(i => graphic.moveBy(i * width, -height)),
    (27 to 37 by 2).map(i => graphic.moveBy(i * width, 25 * height)),
    (-1 to 25 by 2).map(i => graphic.moveBy(27 * width, i * height)),
    (-1 to 25 by 2).map(i => graphic.moveBy(37 * width, i * height))
  ).flatten.map(_.withMaterial(graphic.material.withTint(RGBA.SlateGray)))

  lazy val queuePositions = Seq(Point(33 * width, 4 * height), Point(33 * width, 12 * height), Point(33 * width, 20 * height))

  lazy val dr: Graphic[Material.ImageEffects] = graphic.moveBy(width, height)
  lazy val ddr: Graphic[Material.ImageEffects] = graphic.moveBy(width, 3 * height)
  lazy val drr: Graphic[Material.ImageEffects] = graphic.moveBy(3 * width, height)

  lazy val dl: Graphic[Material.ImageEffects] = graphic.moveBy(-width, height)
  lazy val ddl: Graphic[Material.ImageEffects] = graphic.moveBy(-width, 3 * height)
  lazy val dll: Graphic[Material.ImageEffects] = graphic.moveBy(-3 * width, height)

  lazy val ur: Graphic[Material.ImageEffects] = graphic.moveBy(width, -height)
  lazy val uur: Graphic[Material.ImageEffects] = graphic.moveBy(width, -3 * height)
  lazy val urr: Graphic[Material.ImageEffects] = graphic.moveBy(3 * width, -height)

  lazy val ul: Graphic[Material.ImageEffects] = graphic.moveBy(-width, -height)
  lazy val uul: Graphic[Material.ImageEffects] = graphic.moveBy(-width, -3 * height)
  lazy val ull: Graphic[Material.ImageEffects] = graphic.moveBy(-3 * width, -height)

  lazy val iShape = List(uul, ul, dl, ddl)
  lazy val lShape = List(uul, ul, dl, dr)
  lazy val jShape = List(uul, ul, dl, dr)
  lazy val oShape = List(ul, dl, ur, dr)
  lazy val tShape = List(ull, ul, ur, dl)
  lazy val sShape = List(ull, ul, dl, dr)
  lazy val zShape = List(urr, ur, dr, dl)

  def atPoint(x: Int, y: Int): Graphic[Material.ImageEffects] =
    graphic.moveBy(width * (1 + (2 * x)), height * (1 + (2 * y)))

  def graphicsForShape(shape: PieceShape): List[Graphic[Material.ImageEffects]] = shape match {
    //    case _ => boardEdge
    case PieceShape.IShape => iShape
    case PieceShape.LShape => lShape
    case PieceShape.JShape => jShape
    case PieceShape.OShape => oShape
    case PieceShape.TShape => tShape
    case PieceShape.SShape => sShape
    case PieceShape.ZShape => zShape
  }
