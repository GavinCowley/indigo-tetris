package in.gav.tetris.scenes.menu

class MenuModel(highScore: Seq[(String, Int)])

object MenuModel:
  val Init: MenuModel = MenuModel(Seq.empty)
