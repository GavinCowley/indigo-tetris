package in.gav.tetris.scenes.play.model;

import scala.collection.immutable.Queue

final case class PieceQueue(queue: Queue[PieceShape])(spawner: => PieceShape):
  lazy val dequeue: (PieceShape, PieceQueue) =
    val (head, tail) = queue.enqueue(spawner).dequeue
    (head, PieceQueue(tail)(spawner))

object PieceQueue:
  def init(spawner: => PieceShape): PieceQueue = PieceQueue(Queue.fill(3)(spawner))(spawner)
