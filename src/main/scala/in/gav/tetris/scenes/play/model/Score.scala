package in.gav.tetris.scenes.play.model

case class Score(value: Int, lastBlockScored: Int = 1, streak: Int = 1):
  def add(rows: Int, clearedGrid: Boolean): Score =
    if rows == 0 then
      Score(value)
    else
      val multiplier = if clearedGrid then 10 else 1
      Score(value + (rows * multiplier * lastBlockScored * streak), rows, streak + 1)

object Score:
  val Init: Score = Score(0)