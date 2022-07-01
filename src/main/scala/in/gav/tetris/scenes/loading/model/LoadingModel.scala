package in.gav.tetris.scenes.loading.model

import in.gav.tetris.model.StartUpData
import in.gav.tetris.scenes.play.PlayScene
import indigo.scenes.SceneEvent.JumpTo
import indigo.shared.events.MouseEvent.Click
import indigo.*

case class LoadingModel(countdown: Seconds):

  def update(context: FrameContext[StartUpData], event: GlobalEvent): Outcome[LoadingModel] = event match
    case Click(position) =>
      IndigoLogger.info(s"Clicked: ${position}")
      Outcome(this)
    case FrameTick =>
      if countdown <= context.delta then
        IndigoLogger.info("Loading complete")
        Outcome(LoadingModel(Seconds(0)), List(JumpTo(PlayScene.Name)))
      else Outcome(LoadingModel(countdown - context.delta))
    case _ =>
      Outcome(this)

object LoadingModel:
  val Init: LoadingModel = LoadingModel(Seconds(5))
