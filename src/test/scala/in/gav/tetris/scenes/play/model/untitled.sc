
enum PieceAngle {
  case `0` extends PieceAngle
  case `90` extends PieceAngle
  case `180` extends PieceAngle
  case `270` extends PieceAngle
}

enum PieceShape {
  case I, L, J, O, T, R, Z
}

case class Point(x: Int, y: Int)

def sorted(points: Seq[Point]): List[Point] = {
  points.sortBy(p => (p.x, p.y)).toList
}

sorted(Seq(Point(0, 1), Point(1, 1), Point(1, 0), Point(0, 0), Point(2, 1), Point(1, 2))) ==
  List(Point(0, 0), Point(0, 1), Point(1, 0), Point(1, 1), Point(1, 2), Point(2, 1))

def fromPoints(toAngle: PieceAngle, points: Seq[Point], shape: PieceShape): Seq[Point] = {
  /**
   * |   0   |       90       |  180  |      270       |
   * |  [A]  |                |  [A]  |                |
   * |  [B]. |       .        |  [B]  |  [A][B][C][D]  |
   * |  [C]  |  [A][B][C][D]  | '[C]  |        '       |
   * |  [D]  |                |  [D]  |                |
   */
  val iShape = sorted(points) match {
    case a :: b :: c :: d :: Nil =>
      toAngle match {
        case PieceAngle.`0` => Seq(
          Point(a.x + 1, a.y - 1), Point(b.x, b.y), Point(c.x - 1, c.y + 1), Point(d.x - 2, d.y + 2))
        case PieceAngle.`90` => Seq(
          Point(a.x - 1, a.y + 2), Point(b.x, b.y + 1), Point(c.x + 1, c.y), Point(d.x + 2, d.y - 1))
        case PieceAngle.`180` => Seq(
          Point(a.x + 2, a.y - 2), Point(b.x + 1, b.y - 1), Point(c.x, c.y), Point(d.x - 1, d.y + 1))
        case PieceAngle.`270` => Seq(
          Point(a.x - 2, a.y + 1), Point(b.x - 1, b.y), Point(c.x, c.y - 1), Point(d.x + 1, d.y - 2))
      }
    case _ => points
  }

  /**
   * |    0     |     90      |    180   |     270     |
   * |  [A]     |             |          |             |
   * |  [B].    |       .[C]  |  [A][B]  |  [A][C][D]  |
   * |  [C][D]  |  [A][B][D]  |    '[C]  |  [B]'       |
   * |          |             |     [D]  |             |
   */
  val lShape = sorted(points) match {
    case a :: b :: c :: d :: Nil =>
      toAngle match {
        case PieceAngle.`0` => Seq(
          Point(a.x, a.y - 1), Point(b.x, b.y - 1), Point(c.x - 1, c.y + 1), Point(d.x - 1, d.y + 1))
        case PieceAngle.`90` => Seq(
          Point(a.x - 1, a.y + 2), Point(b.x, b.y + 1), Point(c.x + 1, c.y - 1), Point(d.x, d.y))
        case PieceAngle.`180` => Seq(
          Point(a.x + 1, a.y - 1), Point(b.x + 1, b.y - 1), Point(c.x, c.y + 1), Point(d.x, d.y + 1))
        case PieceAngle.`270` => Seq(
          Point(a.x, a.y), Point(b.x - 1, b.y + 1), Point(c.x, c.y - 1), Point(d.x + 1, d.y - 2))
      }
    case _ => points
  }

  /**
   * |    0     |     90      |    180   |     270     |
   * |     [B]  |             |          |             |
   * |    .[C]  |  [A][B][C]  |  [A][D]  |  [A].       |
   * |  [A][D]  |       '[D]  |  [B]'    |  [B][C][D]  |
   * |          |             |  [C]     |             |
   */
  val jShape = sorted(points) match {
    case a :: b :: c :: d :: Nil =>
      toAngle match {
        case PieceAngle.`0` => Seq(
          Point(a.x, a.y + 1), Point(b.x + 1, b.y - 2), Point(c.x, c.y - 1), Point(d.x - 1, d.y))
        case PieceAngle.`90` => Seq(
          Point(a.x - 1, a.y - 1), Point(b.x - 1, b.y + 1), Point(c.x, c.y), Point(d.x, d.y))
        case PieceAngle.`180` => Seq(
          Point(a.x + 1, a.y), Point(b.x, b.y + 1), Point(c.x - 1, c.y + 2), Point(d.x, d.y - 1))
        case PieceAngle.`270` => Seq(
          Point(a.x, a.y), Point(b.x, b.y), Point(c.x + 1, c.y - 1), Point(d.x + 1, d.y + 1))
      }
    case _ => points
  }

  /**
   * |    0    |     90   |    180    |    270   |
   * |         |     [B]  |           |          |
   * |  .[B]   |  [A][C]  |[A][B][D]  |  [A].    |
   * |[A][C][D]|    '[D]  |   [C]'    |  [B][D]  |
   * |         |          |           |  [C]     |
   */
  val tShape = sorted(points) match {
    case a :: b :: c :: d :: Nil =>
      toAngle match {
        case PieceAngle.`0` => Seq(
          Point(a.x, a.y + 1), Point(b.x + 1, b.y - 1), Point(c.x + 1, c.y - 1), Point(d.x + 1, d.y))
        case PieceAngle.`90` => Seq(
          Point(a.x, a.y - 1), Point(b.x, b.y - 1), Point(c.x, c.y - 1), Point(d.x - 1, d.y))
        case PieceAngle.`180` => Seq(
          Point(a.x, a.y), Point(b.x - 2, b.y + 1), Point(c.x, c.y), Point(d.x - 1, d.y))
        case PieceAngle.`270` => Seq(
          Point(a.x + 1, a.y), Point(b.x, b.y + 1), Point(c.x, c.y + 1), Point(d.x, d.y + 1))
      }
    case _ => points
  }

  /**
   * |    0    |     90   |    180    |    270   |
   * |         |  [A]     |           |          |
   * |  .[B][D]|  [B][C]  |   [B][D]  |  [A].    |
   * |[A][C]   |    '[D]  |[A][C]'    |  [B][C]  |
   * |         |          |           |     [D]  |
   */
  val rShape = sorted(points) match {
    case a :: b :: c :: d :: Nil =>
      toAngle match {
        case PieceAngle.`0` => Seq(
          Point(a.x + 2, a.y), b, c, Point(d.x, d.y - 2))
        case PieceAngle.`90` => Seq(
          Point(a.x, a.y - 2), b, c, Point(d.x - 2, d.y))
        case PieceAngle.`180` => Seq(
          Point(a.x - 1, a.y + 2), b, c, Point(d.x - 1, d.y))
        case PieceAngle.`270` => Seq(
          Point(a.x + 2, a.y), b, c, Point(d.x, d.y + 2))
      }
    case _ => points
  }

  /**
   * |    0    |     90   |    180    |    270   |
   * |         |    .[C]  |           |          |
   * |[A][B].  |  [A][D]  | [A][B]    |    .[C]  |
   * |   [C][D]|  [B]     |   '[C][D] |  [A][D]  |
   * |         |          |           |  [B]     |
   */
  val zShape = sorted(points) match {
    case a :: b :: c :: d :: Nil =>
      toAngle match {
        case PieceAngle.`0` => Seq(
          a, Point(b.x, b.y - 2), Point(c.x - 2, c.y), d)
        case PieceAngle.`90` => Seq(
          Point(a.x + 2, a.y), Point(b.x, b.y + 2), c, d)
        case PieceAngle.`180` => Seq(
          Point(a.x + 2, a.y), Point(b.x, b.y - 2), c, d)
        case PieceAngle.`270` => Seq(
          Point(a.x, a.y + 2), b, c, Point(d.x - 2, d.y))
      }
    case _ => points
  }

  shape match {
    case PieceShape.I => iShape
    case PieceShape.L => lShape
    case PieceShape.J => jShape
    case PieceShape.O => points
    case PieceShape.T => tShape
    case PieceShape.R => rShape
    case PieceShape.Z => zShape
  }
}

val Z0 = sorted(Seq(Point(0, 1), Point(1, 1), Point(1, 2), Point(2, 2)))
val Z90 = sorted(Seq(Point(1, 2), Point(1, 3), Point(2, 1), Point(2, 2)))
val Z180 = sorted(Seq(Point(1, 1), Point(2, 1), Point(2, 2), Point(3, 2)))
val Z270 = sorted(Seq(Point(1, 2), Point(1, 3), Point(2, 1), Point(2, 2)))

val z0 = sorted(fromPoints(PieceAngle.`0`, Z270, PieceShape.Z))
val z90 = sorted(fromPoints(PieceAngle.`90`, Z0, PieceShape.Z))
val z180 = sorted(fromPoints(PieceAngle.`180`, Z90, PieceShape.Z))
val z270 = sorted(fromPoints(PieceAngle.`270`, Z180, PieceShape.Z))

z0 == Z0
z90 == Z90
z180 == Z180
z270 == Z270

val R0 = sorted(Seq(Point(1, 2), Point(2, 1), Point(2, 2), Point(3, 1)))
val R90 = sorted(Seq(Point(1, 0), Point(1, 1), Point(2, 1), Point(2, 2)))
val R180 = sorted(Seq(Point(0, 2), Point(1, 1), Point(1, 2), Point(2, 1)))
val R270 = sorted(Seq(Point(1, 1), Point(1, 2), Point(2, 2), Point(2, 3)))

val r0 = sorted(fromPoints(PieceAngle.`0`, R270, PieceShape.R))
val r90 = sorted(fromPoints(PieceAngle.`90`, R0, PieceShape.R))
val r180 = sorted(fromPoints(PieceAngle.`180`, R90, PieceShape.R))
val r270 = sorted(fromPoints(PieceAngle.`270`, R180, PieceShape.R))

r0 == R0
r90 == R90
r180 == R180
r270 == R270

val T0 = sorted(Seq(Point(1, 2), Point(2, 1), Point(2, 2), Point(3, 2)))
val T90 = sorted(Seq(Point(1, 1), Point(2, 0), Point(2, 1), Point(2, 2)))
val T180 = sorted(Seq(Point(0, 1), Point(1, 1), Point(1, 2), Point(2, 1)))
val T270 = sorted(Seq(Point(1, 1), Point(1, 2), Point(1, 3), Point(2, 2)))

val t0 = sorted(fromPoints(PieceAngle.`0`, T270, PieceShape.T))
val t90 = sorted(fromPoints(PieceAngle.`90`, T0, PieceShape.T))
val t180 = sorted(fromPoints(PieceAngle.`180`, T90, PieceShape.T))
val t270 = sorted(fromPoints(PieceAngle.`270`, T180, PieceShape.T))

t0 == T0
t90 == T90
t180 == T180
t270 == T270

val J0 = sorted(Seq(Point(1, 2), Point(2, 0), Point(2, 1), Point(2, 2)))
val J90 = sorted(Seq(Point(0, 1), Point(1, 1), Point(2, 1), Point(2, 2)))
val J180 = sorted(Seq(Point(1, 1), Point(1, 2), Point(1, 3), Point(2, 1)))
val J270 = sorted(Seq(Point(1, 1), Point(1, 2), Point(2, 2), Point(3, 2)))

val j0 = sorted(fromPoints(PieceAngle.`0`, J270, PieceShape.J))
val j90 = sorted(fromPoints(PieceAngle.`90`, J0, PieceShape.J))
val j180 = sorted(fromPoints(PieceAngle.`180`, J90, PieceShape.J))
val j270 = sorted(fromPoints(PieceAngle.`270`, J180, PieceShape.J))

j0 == J0
j90 == J90
j180 == J180
j270 == J270

val L0 = sorted(Seq(Point(1, 0), Point(1, 1), Point(1, 2), Point(2, 2)))
val L90 = sorted(Seq(Point(2, 1), Point(0, 2), Point(1, 2), Point(2, 2)))
val L180 = sorted(Seq(Point(2, 1), Point(2, 2), Point(2, 3), Point(1, 1)))
val L270 = sorted(Seq(Point(1, 1), Point(1, 2), Point(2, 1), Point(3, 1)))

val l0 = sorted(fromPoints(PieceAngle.`0`, L270, PieceShape.L))
val l90 = sorted(fromPoints(PieceAngle.`90`, L0, PieceShape.L))
val l180 = sorted(fromPoints(PieceAngle.`180`, L90, PieceShape.L))
val l270 = sorted(fromPoints(PieceAngle.`270`, L180, PieceShape.L))

l0 == L0
l90 == L90
l180 == L180
l270 == L270

val I0 = sorted(Seq(Point(1, 0), Point(1, 1), Point(1, 3), Point(1, 2)))
val I90 = sorted(Seq(Point(1, 2), Point(0, 2), Point(2, 2), Point(3, 2)))
val I180 = sorted(Seq(Point(2, 2), Point(2, 0), Point(2, 1), Point(2, 3)))
val I270 = sorted(Seq(Point(0, 1), Point(2, 1), Point(3, 1), Point(1, 1)))

val i0 = sorted(fromPoints(PieceAngle.`0`, I270, PieceShape.I))
val i90 = sorted(fromPoints(PieceAngle.`90`, I0, PieceShape.I))
val i180 = sorted(fromPoints(PieceAngle.`180`, I90, PieceShape.I))
val i270 = sorted(fromPoints(PieceAngle.`270`, I180, PieceShape.I))

i0 == I0
i90 == I90
i180 == I180
i270 == I270