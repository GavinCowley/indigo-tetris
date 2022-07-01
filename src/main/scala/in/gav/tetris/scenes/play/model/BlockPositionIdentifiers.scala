package in.gav.tetris.scenes.play.model

import indigo.shared.datatypes.Point

/**
 * [AA][BA][CA][DA]
 * [AB][BB][CB][DB]
 * [AC][BC][CC][DC]
 * [AD][BD][CD][DD]
 */
object BlockPositionIdentifiers:

  val AA: Point = Point(0, 0)
  val AB: Point = Point(0, 1)
  val AC: Point = Point(0, 2)
  val AD: Point = Point(0, 3)

  val BA: Point = Point(1, 0)
  val BB: Point = Point(1, 1)
  val BC: Point = Point(1, 2)
  val BD: Point = Point(1, 3)

  val CA: Point = Point(2, 0)
  val CB: Point = Point(2, 1)
  val CC: Point = Point(2, 2)
  val CD: Point = Point(2, 3)

  val DA: Point = Point(3, 0)
  val DB: Point = Point(3, 1)
  val DC: Point = Point(3, 2)
  val DD: Point = Point(3, 3)
