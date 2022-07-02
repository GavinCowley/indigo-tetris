package in.gav.tetris.view

import in.gav.tetris.model.StartUpData
import in.gav.tetris.scenes.loading.LoadingView
import in.gav.tetris.scenes.menu.MenuView
import in.gav.tetris.scenes.play.PlayView

final case class View(loadingView: LoadingView, menuView: MenuView, playView: PlayView)

object View:
  def init(startupData: StartUpData): View = View(LoadingView.Init, MenuView.init(startupData), PlayView.Init)
