package in.gav.tetris.scenes.loading

import indigo.*
import indigo.scenes.{Lens, Scene, SceneName}
import indigoextras.subsystems.AssetBundleLoader
import indigoextras.trees.QuadTree
import in.gav.tetris.model.{Model, StartUpData}
import in.gav.tetris.scenes.loading.model.LoadingModel
import in.gav.tetris.scenes.loading.view.LoadingView
import in.gav.tetris.view.View

final case class LoadingScene(screenDimensions: Rectangle) extends Scene[StartUpData, Model, View] :
  override type SceneModel = LoadingModel
  override type SceneViewModel = LoadingView

  override def name: SceneName = LoadingScene.Name

  override def modelLens: Lens[Model, LoadingModel] = Lens(_.loadingModel, (m, p) => m.copy(loadingModel = p))

  override def viewModelLens: Lens[View, LoadingView] = Lens(_.loadingView, (v, p) => v.copy(loadingView = p))

  override def eventFilters: EventFilters = EventFilters.Restricted

  override def subSystems: Set[SubSystem] = Set(AssetBundleLoader)

  override def updateModel(context: FrameContext[StartUpData], model: LoadingModel): GlobalEvent => Outcome[LoadingModel] =
    model.update(context, _)

  override def updateViewModel(context: FrameContext[StartUpData], model: LoadingModel, viewModel: LoadingView): GlobalEvent => Outcome[LoadingView] =
    viewModel.update(context, _)

  override def present(context: FrameContext[StartUpData], model: LoadingModel, viewModel: LoadingView): Outcome[SceneUpdateFragment] =
    viewModel.draw(context, model)


object LoadingScene:
  final val Name: SceneName = SceneName("Loading")


