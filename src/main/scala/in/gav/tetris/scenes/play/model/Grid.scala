package in.gav.tetris.scenes.play.model;

import indigo.{Point, Size}

import scala.annotation.tailrec

case class Grid(blocks: Seq[Block]):

  lazy val hasFailed: Boolean = blocks.exists(_.isOffGrid)
  lazy val isEmpty: Boolean = !blocks.exists(_.state.isSolid)

  def evaluate: (Grid, Int) =
    blocks.groupBy(_.point.y).toSeq
      .sortBy(_._1)(Ordering.Int.reverse)
      .foldLeft((Grid.Empty, 0)) { case ((nextGrid, ys), (y, row)) =>
        assert(row.forall(_._1.y == y), "Had a bad y in the fold left")
        if row.forall(_._2.isSolid) then (nextGrid, ys + 1)
        else (nextGrid.addBlocks(row.map(p => Block(Point(p._1.x, p._1.y + ys), p._2))), ys)
      }

  def changeBlocks(points: Seq[Point], state: BlockState): Grid =
    Grid(blocks.filter(x => !points.contains(x._1)) ++ points.map(Block(_, state)))

  def canMove(points: Seq[Point]): Boolean =
    blocks.filter(x => points.contains(x._1)).forall(!_._2.isSolid)

  def maxDropPoints(points: Seq[Point]): Seq[Point] =
    @tailrec
    def rec(i: Int): Int = {
      val downPoints: Seq[Point] = points.map(b => Point(b.x, b.y + i + 1))
      if canMove(downPoints) && clearBottomWall(downPoints) && i <= Grid.BottomWall then rec(i + 1) else i
    }

    val maxDrop = rec(0)
    points.map(b => Point(b.x, b.y + maxDrop))

  def clearLeftWall(points: Seq[Point]): Boolean = points.minByOption(_.x).exists(_.x >= 0)

  def clearRightWall(points: Seq[Point]): Boolean = points.maxByOption(_.x).exists(_.x < Grid.RightWall)

  def clearBottomWall(points: Seq[Point]): Boolean = points.maxByOption(_.y).exists(_.y < Grid.BottomWall)

  def clearAllWall(points: Seq[Point]): Boolean =
    clearLeftWall(points) && clearRightWall(points) && clearBottomWall(points)

  def addBlocks(moreBlocks: Seq[Block]): Grid = Grid(blocks ++ moreBlocks)

object Grid:
  // todo: remove empty blocks, replace with nothing
  val GridSize = Size(10, 20)
  val RightWall: Int = GridSize.width
  val BottomWall: Int = GridSize.height

  val Empty: Grid = Grid(Seq.empty)
  val Init: Grid = Grid(Seq.tabulate(RightWall, BottomWall)(Block.apply).flatten)
