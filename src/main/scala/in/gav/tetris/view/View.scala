package in.gav.tetris.view

import in.gav.tetris.scenes.play.view.PlayView
import in.gav.tetris.scenes.loading.view.LoadingView
import in.gav.tetris.scenes.menu.view.MenuView

final case class View(loadingView: LoadingView, menuView: MenuView, playView: PlayView)

object View:
  val Init: View = View(LoadingView.Init, MenuView.Init, PlayView.Init)
