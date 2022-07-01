package in.gav.tetris.scenes.play

import indigo.scenes.{Lens, Scene, SceneName}
import indigo.*
import indigoextras.trees.QuadTree
import in.gav.tetris.model.{Model, StartUpData}
import in.gav.tetris.scenes.play.model.PlayModel
import in.gav.tetris.scenes.play.view.PlayView
import in.gav.tetris.view.View

final case class PlayScene() extends Scene[StartUpData, Model, View] :
  override type SceneModel = PlayModel
  override type SceneViewModel = PlayView

  override def name: SceneName = PlayScene.Name

  override def modelLens: Lens[Model, PlayModel] = Lens(_.playModel, (m, p) => m.copy(playModel = p))

  override def viewModelLens: Lens[View, PlayView] = Lens(_.playView, (v, p) => v.copy(playView = p))

  override def eventFilters: EventFilters = EventFilters.Restricted

  override def subSystems: Set[SubSystem] = Set.empty[SubSystem]

  override def updateModel(context: FrameContext[StartUpData], model: PlayModel): GlobalEvent => Outcome[PlayModel] =
    model.update(context, _)

  override def updateViewModel(context: FrameContext[StartUpData], model: PlayModel, viewModel: PlayView): GlobalEvent => Outcome[PlayView] =
    viewModel.update(context, _)

  override def present(context: FrameContext[StartUpData], model: PlayModel, viewModel: PlayView): Outcome[SceneUpdateFragment] =
    viewModel.draw(context, model)

object PlayScene:
  final val Name: SceneName = SceneName("Play")
