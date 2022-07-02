package in.gav.tetris.model

import in.gav.tetris.scenes.loading.LoadingModel
import in.gav.tetris.scenes.play.model.PlayModel
import in.gav.tetris.scenes.menu.MenuModel

final case class Model(loadingModel: LoadingModel, menuModel: MenuModel, playModel: PlayModel)

object Model:
  val Init: Model = Model(LoadingModel.Init, MenuModel.Init, PlayModel.Init)
