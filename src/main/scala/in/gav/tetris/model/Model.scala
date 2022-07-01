package in.gav.tetris.model

import in.gav.tetris.scenes.play.model.PlayModel
import in.gav.tetris.scenes.loading.model.LoadingModel

final case class Model(loadingModel: LoadingModel, playModel: PlayModel)

object Model:
  val Init: Model = Model(LoadingModel.Init, PlayModel.Init)
